package com.example.testapp.data.exception

class NetworkConnectionError : Exception("No connection to WiFi or mobile internet")

class ServerError(error: String) : Exception(error)