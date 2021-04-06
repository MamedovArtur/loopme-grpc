package com.example.grpc.integration;

import com.google.common.util.concurrent.ListenableFuture;
import com.loopme.test.CapitalizeRequest;
import com.loopme.test.CapitalizeResponse;
import com.loopme.test.CapitalizeServiceGrpc;
import io.grpc.Status;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = {
        "grpc.server.inProcessName=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@DirtiesContext
@SpringJUnitConfig(classes = {TestConfuguration.class})
class CapitalizeServiceImplIntegrationTest {

    private final static int MAX_REQUEST_TIME = 10;

    @GrpcClient("inProcess")
    private CapitalizeServiceGrpc.CapitalizeServiceFutureStub capitalizeService;


    @Test
    @DisplayName("CapitalizeService normal work test")
    @DirtiesContext
    public void testNormal() throws Exception {
        CapitalizeRequest request = CapitalizeRequest.newBuilder()
                .setStr("hello world")
                .build();


        ListenableFuture<CapitalizeResponse> responseFuture = capitalizeService.capitalize(request);
        try {
            CapitalizeResponse response = responseFuture.get(MAX_REQUEST_TIME, TimeUnit.SECONDS);
            assertEquals("Hello world", response.getStr());
        } catch (TimeoutException timeoutException) {
            fail("DEADLINE");
        }


    }

    @Test
    @DisplayName("CapitalizeService invalid arguments")
    @DirtiesContext
    public void testInvalidArguments() {
        CapitalizeRequest request = CapitalizeRequest.newBuilder()
                .setStr("a".repeat(110))//len>100
                .build();

        try {
            capitalizeService.capitalize(request).get();
            fail("METHOD MUST THROWN");
        } catch (Exception e) {
            assertEquals(Status.Code.INVALID_ARGUMENT,Status.fromThrowable(e).getCode());
        }

    }
}