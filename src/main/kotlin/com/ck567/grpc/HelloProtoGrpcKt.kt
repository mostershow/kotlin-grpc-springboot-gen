package com.ck567.grpc

import com.ck567.grpc.HelloServiceGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for com.ck567.grpc.HelloService.
 */
object HelloServiceGrpcKt {
    @JvmStatic
    val serviceDescriptor: ServiceDescriptor
        get() = HelloServiceGrpc.getServiceDescriptor()

    val sayHelloMethod: MethodDescriptor<HelloRequest, HelloResponse>
        @JvmStatic
        get() = HelloServiceGrpc.getSayHelloMethod()

    /**
     * A stub for issuing RPCs to a(n) com.ck567.grpc.HelloService service as suspending coroutines.
     */
    @StubFor(HelloServiceGrpc::class)
    class HelloServiceCoroutineStub @JvmOverloads constructor(
        channel: Channel,
        callOptions: CallOptions = DEFAULT
    ) : AbstractCoroutineStub<HelloServiceCoroutineStub>(channel, callOptions) {
        override fun build(channel: Channel, callOptions: CallOptions): HelloServiceCoroutineStub =
            HelloServiceCoroutineStub(channel, callOptions)

        /**
         * Executes this RPC and returns the response message, suspending until the RPC completes
         * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
         * corresponding
         * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
         * with the corresponding exception as a cause.
         *
         * @param request The request message to send to the server.
         *
         * @return The single response from the server.
         */
        suspend fun sayHello(request: HelloRequest): HelloResponse = unaryRpc(
            channel,
            HelloServiceGrpc.getSayHelloMethod(),
            request,
            callOptions,
            Metadata()
        )}

    /**
     * Skeletal implementation of the com.ck567.grpc.HelloService service based on Kotlin coroutines.
     */
    abstract class HelloServiceCoroutineImplBase(
        coroutineContext: CoroutineContext = EmptyCoroutineContext
    ) : AbstractCoroutineServerImpl(coroutineContext) {
        /**
         * Returns the response to an RPC for com.ck567.grpc.HelloService.sayHello.
         *
         * If this method fails with a [StatusException], the RPC will fail with the corresponding
         * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
         * the RPC will fail
         * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
         * fail with `Status.UNKNOWN` with the exception as a cause.
         *
         * @param request The request from the client.
         */
        open suspend fun sayHello(request: HelloRequest): HelloResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method com.ck567.grpc.HelloService.sayHello is unimplemented"))

        final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
            .addMethod(unaryServerMethodDefinition(
                context = this.context,
                descriptor = HelloServiceGrpc.getSayHelloMethod(),
                implementation = ::sayHello
            )).build()
    }
}
