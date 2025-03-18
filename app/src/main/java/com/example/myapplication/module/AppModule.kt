package com.example.myapplication.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.metamask.androidsdk.*

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    fun provideDappMetadata(@ApplicationContext context: Context): DappMetadata {
        val dappMetadata = DappMetadata(
            "Mediva",
            "https://${context.applicationInfo.name}.com"
        )
        return dappMetadata
    }

    @Provides
    fun provideEthereumFlow(@ApplicationContext context: Context, dappMetadata: DappMetadata): EthereumFlow {
        val infuraApiKey = "9b1d1bebd19d4fccab1822cb7bad40de"
        val readonlyRPCMap = mapOf("0x539" to "http://192.168.254.164:8545")
        val sdkOptions = SDKOptions(
            infuraAPIKey = infuraApiKey,
            readonlyRPCMap = readonlyRPCMap,
        )

        val ethereum = Ethereum(context, dappMetadata, sdkOptions)
        return EthereumFlow(ethereum)
    }
}