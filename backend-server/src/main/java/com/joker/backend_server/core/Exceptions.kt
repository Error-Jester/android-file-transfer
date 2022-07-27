package com.joker.backend_server.core

import java.lang.Exception

abstract class Exceptions: Exception()

class ServerException: Exceptions()