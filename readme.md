#Example gRpc Spring project
реализует сервис

```protobuf
syntax = "proto3";

package loopme.test;

option java_multiple_files = true;
option java_outer_classname = "LoopmeTestProto";
option java_package = "com.loopme.test";

service CapitalizeService {
  rpc Capitalize (CapitalizeRequest) returns (CapitalizeResponse){}
}

message CapitalizeRequest {
  // max length 100
  string str = 1;
}

message CapitalizeResponse {
  string str = 1;
}
```


Сборка проекта осуществляется при помощи maven
```bash
mvn package
```

если необходимо запустить только тесты
```bash
mvn test
```
запустить приложение можно командой
```bash
mvn spring-boot:run
```

После сборки в папке 
`<project-root>/target` будет создан файл `grpc-example.jar`
являющийся исполняемым jar (SpringBoot), 
запустить файл можно командой
```bash
java -jar <project-root>/target/grpc-example.jar
```


Пример обращения к сервису через grpcurl

```bash
grpcurl --plaintext -d '{"str":"vasia"}' <host>:9090 loopme.test.CapitalizeService.Capitalize 
```

