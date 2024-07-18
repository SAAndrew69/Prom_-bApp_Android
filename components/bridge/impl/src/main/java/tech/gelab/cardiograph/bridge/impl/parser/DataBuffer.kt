package tech.gelab.cardiograph.bridge.impl.parser

class DataBuffer(initial: ByteArray? = null) {

    companion object {
        // TODO tune
        const val DEFAULT_BUFFER_CAPACITY = 10
    }

    var data: ByteArray? = initial
        private set

    var size: Int = data?.size ?: 0
        private set

    var position: Int = 0
        private set

    constructor() : this(ByteArray(DEFAULT_BUFFER_CAPACITY))

    constructor(capacity: Int) : this(ByteArray(capacity))

    init {
        size(capacity())
    }

    /**
     * Buffer capacity.
     *
     * @return Buffer capacity.
     */
    fun capacity(): Int {
        return if (data == null) {
            0
        } else data!!.size
    }

    /**
     * Allocate new size for the array in bytes.
     *
     * @param capacity
     * Buffer capacity.
     */
    fun capacity(capacity: Int) {
        if (capacity == 0) {
            data = null
            size = 0
            position = 0
        } else {
            if (data == null) {
                data = ByteArray(capacity)
            } else {
                val tmp: ByteArray = data!!
                data = ByteArray(capacity)
                if (size < capacity) {
                    System.arraycopy(tmp, 0, data!!, 0, size)
                } else {
                    System.arraycopy(tmp, 0, data!!, 0, capacity)
                }
            }
        }
    }

    /**
     * @param value
     * Buffer size.
     */
    fun size(value: Int) {
        require(!(value < 0 || value > capacity())) { "size" }
        size = value
        if (position > size) {
            position = size
        }
    }

    /**
     * @return Amount of non read bytes in the buffer.
     */
    fun available(): Int {
        return size - position
    }

    /**
     * Push the given byte into this buffer at the current position, and then
     * increments the position.
     *
     * @param item
     * The byte to be added.
     */
    fun setUInt8(item: Byte) {
        setUInt8(size, item)
        ++size
    }

    /**
     * Set the UInt8 value into this buffer for given position, and then
     * increments the position.
     *
     * @param index
     * Buffer index where value is set.
     * @param item
     * The UInt8 value to be added.
     */
    fun setUInt8(index: Int, item: Byte) {
        if (index >= capacity()) {
            capacity(index + DEFAULT_BUFFER_CAPACITY)
        }
        data!![index] = item
    }

    /**
     * Push the UInt16 value into this buffer at the current position, and then
     * increments the position.
     *
     * @param item
     * The UInt16 value to be added.
     */
    fun setUInt16(item: Int) {
        setUInt16(size, item)
        size += 2
    }

    /**
     * Set the UInt16 value into this buffer for the given position, and then
     * increments the position.
     *
     * @param index
     * Buffer index where value is set.
     * @param item
     * The UInt16 value to be added.
     */
    fun setUInt16(index: Int, item: Int) {
        if (index + 2 >= capacity()) {
            capacity(index + DEFAULT_BUFFER_CAPACITY)
        }
        data!![index] = (item shr 8 and 0xFF).toByte()
        data!![index + 1] = (item and 0xFF).toByte()
    }

    /**
     * Push the UInt32 value into this buffer at the current position, and then
     * increments the position.
     *
     * @param item
     * The UInt32 value to be added.
     */
    fun setUInt32(item: Long) {
        setUInt32(size, item)
        size += 4
    }

    /**
     * Set the UInt32 value into this buffer for the given position, and then
     * increments the position.
     *
     * @param index
     * Buffer index where value is set.
     * @param item
     * The UInt32 value to be added.
     */
    fun setUInt32(index: Int, item: Long) {
        if (index + 4 >= capacity()) {
            capacity(index + DEFAULT_BUFFER_CAPACITY)
        }
        data!![index] = (item shr 24 and 0xFFL).toByte()
        data!![index + 1] = (item shr 16 and 0xFFL).toByte()
        data!![index + 2] = (item shr 8 and 0xFFL).toByte()
        data!![index + 3] = (item and 0xFFL).toByte()
    }

    /**
     * @return UInt8 value from byte buffer.
     */
    fun getUInt8(): UByte {
        val value = getUInt8(position)
        ++position
        return value
    }

    /**
     * @return Int8 value from byte buffer.
     */
    fun getInt8(): Byte {
        return getUInt8().toByte()
    }

    /**
     * Get UInt8 value from byte buffer from the given index..
     *
     * @param index
     * Buffer index.
     * @return UInt8 value.
     */
    fun getUInt8(index: Int): UByte {
        if (index >= size) {
            throw IllegalArgumentException("getUInt8: index = $index, size = $size")
        }
        return (data!![index].toInt() and 0xFF).toUByte()
    }

    /**
     * @return UInt16 value from byte buffer.
     */
    fun getUInt16(): Int {
        val value = getUInt16(position)
        position += 2
        return value
    }

    /**
     * @return Int16 value from byte buffer.
     */
    fun getInt16(): Short {
        return getUInt16().toShort()
    }

    /**
     * Get UInt16 value from byte buffer from the given index..
     *
     * @param index
     * Buffer index.
     * @return UInt16 value.
     */
    fun getUInt16(index: Int): Int {
        if (index + 2 > size) {
            throw IllegalArgumentException("getUInt16: index = $index, size = $size")
        }
        return data!![index].toInt() and 0xFF shl 8 or (data!![index + 1].toInt() and 0xFF)
    }

    /**
     * Get Int32 value from the current position.
     *
     * @return Int32 value.
     */
    fun getUInt32(): Long {
        val value = getUInt32(position)
        position += 4
        return value
    }

    /**
     * Get Int32 value from the current position.
     *
     * @return Int32 value.
     */
    fun getInt32(): Int {
        val value = getInt32(position)
        position += 4
        return value
    }

    /**
     * Get UInt32 value from byte buffer from the given index..
     *
     * @param index
     * Buffer index.
     * @return UInt32 value.
     */
    fun getInt32(index: Int): Int {
        if (index + 4 > size) {
            throw IllegalArgumentException("getUInt32: index = $index, size = $size")
        }
        return data!![index].toInt() and 0xFF shl 24 or (data!![index + 1].toInt() and 0xFF shl 16
                ) or (data!![index + 2].toInt() and 0xFF shl 8) or (data!![index + 3].toInt() and 0xFF)
    }

    /**
     * Get UInt32 value from byte buffer from the given index..
     *
     * @param index
     * Buffer index.
     * @return UInt32 value.
     */
    fun getUInt32(index: Int): Long {
        if (index + 4 > size) {
            throw IllegalArgumentException("getUInt32: index = $index, size = $size")
        }
        var value = (data!![index].toInt() and 0xFF).toLong()
        value = value shl 24
        value = value or (data!![index + 1].toInt() and 0xFF shl 16).toLong()
        value = value or (data!![index + 2].toInt() and 0xFF shl 8).toLong()
        value = value or (data!![index + 3].toInt() and 0xFF).toLong()
        return value
    }

}