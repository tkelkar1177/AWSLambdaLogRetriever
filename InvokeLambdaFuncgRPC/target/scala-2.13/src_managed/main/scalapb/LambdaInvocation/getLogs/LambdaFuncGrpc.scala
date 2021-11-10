package LambdaInvocation.getLogs

object LambdaFuncGrpc {
  val METHOD_RETRIEVE_LOGS: _root_.io.grpc.MethodDescriptor[LambdaInvocation.getLogs.LambdaInvokeRequest, LambdaInvocation.getLogs.LambdaInvokeReply] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("LambdaInvocation.LambdaFunc", "RetrieveLogs"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[LambdaInvocation.getLogs.LambdaInvokeRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[LambdaInvocation.getLogs.LambdaInvokeReply])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(LambdaInvocation.getLogs.GetLogsProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("LambdaInvocation.LambdaFunc")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(LambdaInvocation.getLogs.GetLogsProto.javaDescriptor))
      .addMethod(METHOD_RETRIEVE_LOGS)
      .build()
  
  trait LambdaFunc extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = LambdaFunc
    def retrieveLogs(request: LambdaInvocation.getLogs.LambdaInvokeRequest): scala.concurrent.Future[LambdaInvocation.getLogs.LambdaInvokeReply]
  }
  
  object LambdaFunc extends _root_.scalapb.grpc.ServiceCompanion[LambdaFunc] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[LambdaFunc] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = LambdaInvocation.getLogs.GetLogsProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = LambdaInvocation.getLogs.GetLogsProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: LambdaFunc, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_RETRIEVE_LOGS,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[LambdaInvocation.getLogs.LambdaInvokeRequest, LambdaInvocation.getLogs.LambdaInvokeReply] {
          override def invoke(request: LambdaInvocation.getLogs.LambdaInvokeRequest, observer: _root_.io.grpc.stub.StreamObserver[LambdaInvocation.getLogs.LambdaInvokeReply]): _root_.scala.Unit =
            serviceImpl.retrieveLogs(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait LambdaFuncBlockingClient {
    def serviceCompanion = LambdaFunc
    def retrieveLogs(request: LambdaInvocation.getLogs.LambdaInvokeRequest): LambdaInvocation.getLogs.LambdaInvokeReply
  }
  
  class LambdaFuncBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LambdaFuncBlockingStub](channel, options) with LambdaFuncBlockingClient {
    override def retrieveLogs(request: LambdaInvocation.getLogs.LambdaInvokeRequest): LambdaInvocation.getLogs.LambdaInvokeReply = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_RETRIEVE_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LambdaFuncBlockingStub = new LambdaFuncBlockingStub(channel, options)
  }
  
  class LambdaFuncStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LambdaFuncStub](channel, options) with LambdaFunc {
    override def retrieveLogs(request: LambdaInvocation.getLogs.LambdaInvokeRequest): scala.concurrent.Future[LambdaInvocation.getLogs.LambdaInvokeReply] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_RETRIEVE_LOGS, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LambdaFuncStub = new LambdaFuncStub(channel, options)
  }
  
  def bindService(serviceImpl: LambdaFunc, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = LambdaFunc.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): LambdaFuncBlockingStub = new LambdaFuncBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): LambdaFuncStub = new LambdaFuncStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = LambdaInvocation.getLogs.GetLogsProto.javaDescriptor.getServices().get(0)
  
}