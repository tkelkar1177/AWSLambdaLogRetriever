package io.grpc.examples.helloworld.getLogs

object GreeterGrpc {
  val METHOD_RETRIEVE_LOGS: _root_.io.grpc.MethodDescriptor[io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest, io.grpc.examples.helloworld.getLogs.LambdaInvokeReply] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("LambdaInvocation.Greeter", "RetrieveLogs"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[io.grpc.examples.helloworld.getLogs.LambdaInvokeReply])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(io.grpc.examples.helloworld.getLogs.GetLogsProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("LambdaInvocation.Greeter")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(io.grpc.examples.helloworld.getLogs.GetLogsProto.javaDescriptor))
      .addMethod(METHOD_RETRIEVE_LOGS)
      .build()
  
  trait Greeter extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = Greeter
    def retrieveLogs(request: io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest): scala.concurrent.Future[io.grpc.examples.helloworld.getLogs.LambdaInvokeReply]
  }
  
  object Greeter extends _root_.scalapb.grpc.ServiceCompanion[Greeter] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[Greeter] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = io.grpc.examples.helloworld.getLogs.GetLogsProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = io.grpc.examples.helloworld.getLogs.GetLogsProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_RETRIEVE_LOGS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest, io.grpc.examples.helloworld.getLogs.LambdaInvokeReply] {
          override def invoke(request: io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest, observer: _root_.io.grpc.stub.StreamObserver[io.grpc.examples.helloworld.getLogs.LambdaInvokeReply]): _root_.scala.Unit =
            serviceImpl.retrieveLogs(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait GreeterBlockingClient {
    def serviceCompanion = Greeter
    def retrieveLogs(request: io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest): io.grpc.examples.helloworld.getLogs.LambdaInvokeReply
  }
  
  class GreeterBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterBlockingStub](channel, options) with GreeterBlockingClient {
    override def retrieveLogs(request: io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest): io.grpc.examples.helloworld.getLogs.LambdaInvokeReply = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_RETRIEVE_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterBlockingStub = new GreeterBlockingStub(channel, options)
  }
  
  class GreeterStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterStub](channel, options) with Greeter {
    override def retrieveLogs(request: io.grpc.examples.helloworld.getLogs.LambdaInvokeRequest): scala.concurrent.Future[io.grpc.examples.helloworld.getLogs.LambdaInvokeReply] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_RETRIEVE_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterStub = new GreeterStub(channel, options)
  }
  
  def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = Greeter.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): GreeterBlockingStub = new GreeterBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): GreeterStub = new GreeterStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = io.grpc.examples.helloworld.getLogs.GetLogsProto.javaDescriptor.getServices().get(0)
  
}