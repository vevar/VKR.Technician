package com.nstu.technician.data

class BodyNotFoundException(tag: String, method: String) : Throwable("$tag: body not found ($method)")

const val BODY_MUST_BE_SET = "Body must be set"