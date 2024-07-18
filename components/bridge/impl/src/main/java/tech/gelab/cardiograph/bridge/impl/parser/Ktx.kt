package tech.gelab.cardiograph.bridge.impl.parser

fun ByteArray.toHexString(): String {
    return this.joinToString(" ") { "%02x".format(it) }
}