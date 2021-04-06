package com.example.grpc.grpc;

import com.loopme.test.CapitalizeRequest;
import com.loopme.test.CapitalizeResponse;
import com.loopme.test.CapitalizeServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.util.StringUtils;

import java.util.Objects;

@GrpcService
@Slf4j
public class CapitalizeServiceImpl extends CapitalizeServiceGrpc.CapitalizeServiceImplBase {

    private final static int MAX_LEN = 100;

    @Override
    public void capitalize(CapitalizeRequest request, StreamObserver<CapitalizeResponse> responseObserver) {
        log.info("CapitalizeServiceImpl:capitalize request {}",request);

        String incomingString = request.getStr();

        if(Objects.isNull(incomingString) || incomingString.length() > MAX_LEN)
            throw new IllegalArgumentException();

        responseObserver.onNext(CapitalizeResponse.newBuilder()
                .setStr(StringUtils.capitalize(incomingString))
                .build());


        responseObserver.onCompleted();
    }
}
