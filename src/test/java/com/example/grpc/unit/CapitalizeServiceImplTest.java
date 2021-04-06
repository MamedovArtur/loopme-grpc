package com.example.grpc.unit;

import com.example.grpc.grpc.CapitalizeServiceImpl;
import com.loopme.test.CapitalizeRequest;
import com.loopme.test.CapitalizeResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CapitalizeServiceImplTest {
    private CapitalizeServiceImpl capitalizeService;
    private final static int MAX_REQUEST_TIME = 10;

    @BeforeEach
    public void setup() {
        capitalizeService = new CapitalizeServiceImpl();
    }


    @Test
    @DisplayName("CapitalizeService normal work test")
    public void testNormal() throws Exception {
        CapitalizeRequest request = CapitalizeRequest.newBuilder()
                .setStr("hello world")
                .build();

        StreamRecorder<CapitalizeResponse> recorder = StreamRecorder.create();

        capitalizeService.capitalize(request,recorder);
        if (!recorder.awaitCompletion(MAX_REQUEST_TIME, TimeUnit.SECONDS)) {
            fail("DEADLINE");
        }

        assertNull(recorder.getError());
        List<CapitalizeResponse> result = recorder.getValues();
        assertEquals(1,result.size());
        CapitalizeResponse response = result.get(0);
        assertEquals("Hello world",response.getStr());
    }

    @Test
    @DisplayName("CapitalizeService invalid arguments")
    public void testInvalidArguments() throws Exception {

        CapitalizeRequest request = CapitalizeRequest.newBuilder()
                .setStr("a".repeat(110))//len>100
                .build();

        StreamRecorder<CapitalizeResponse> recorder = StreamRecorder.create();

        assertThrows(
                IllegalArgumentException.class,
                () -> capitalizeService.capitalize( request, recorder )
        );

    }
}