package com.joker.backend_server

import android.util.Log
import java.lang.Exception
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface

object NetworkUtils {
    fun getLocalIpAddress(): String? = getInetAddresses()
        .filter { it.isLocalAddress() }
        .map { it.hostAddress }
        .firstOrNull()

    private fun getInetAddresses() = NetworkInterface.getNetworkInterfaces()
        .iterator()
        .asSequence()
        .flatMap {
            net ->
                net.inetAddresses
                    .asSequence()
                    .filter { !it.isLoopbackAddress }
        }.toList()

    fun InetAddress.isLocalAddress(): Boolean {
        try {
            return isSiteLocalAddress
                    && !hostAddress!!.contains(":")
                    && hostAddress != "127.0.0.1"
        } catch (e: Exception) {
            Log.e("Getting Local Address", e.toString())
        }
        return false
    }
}