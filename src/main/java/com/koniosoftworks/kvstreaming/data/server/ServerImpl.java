package com.koniosoftworks.kvstreaming.data.server;

import com.google.inject.Inject;
import com.koniosoftworks.kvstreaming.domain.concurrency.TaskScheduler;
import com.koniosoftworks.kvstreaming.domain.props.ServerProperties;
import com.koniosoftworks.kvstreaming.domain.server.Server;
import com.koniosoftworks.kvstreaming.utils.Formatting;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nicu on 5/15/17.
 */
public class ServerImpl implements Server {
    private ServerSocket serverSocket;
    private final ClientsPool clientsPool;
    private final TaskScheduler taskScheduler;
    private final Logger logger;

    @Inject
    public ServerImpl(TaskScheduler taskScheduler, ClientsPool clientsPool, Logger logger) {
        this.taskScheduler = taskScheduler;
        this.clientsPool = clientsPool;
        this.logger = logger;
    }

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started at " + Formatting.getConnectionInfo(serverSocket));
            serverSocket.setSoTimeout(180000);
            taskScheduler.run(this::waitForConnections);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void stop() {
        try {
            taskScheduler.stopAll();
            clientsPool.dropConnections();
            serverSocket.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void waitForConnections() {
        logger.info("Waiting for connections");
        while (!serverSocket.isClosed()) {
            if (clientsPool.getSize() < ServerProperties.MAX_CONNECTIONS_ALLOWED - 1) {
                try {
                    Socket socket = serverSocket.accept();
                    clientsPool.addNewClient(socket);
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }
}
