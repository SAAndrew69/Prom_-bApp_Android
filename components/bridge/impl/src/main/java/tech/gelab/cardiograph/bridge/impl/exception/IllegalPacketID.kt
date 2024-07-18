package tech.gelab.cardiograph.bridge.impl.exception

class IllegalPacketID(id: Int) : ParseException("Got illegal packet id: $id")