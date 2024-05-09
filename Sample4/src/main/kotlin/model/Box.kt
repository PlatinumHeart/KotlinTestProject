package org.example.model

data class Box(
    val intVal: Int,
    var isBoolean: Boolean,
    val floatVal: Float,
    val listVal: List<Box>
) {
    fun getIsMyBoolean(): Boolean {
        return isBoolean
    }

    fun setIsMyBoolean(isBoolean: Boolean) {
        this.isBoolean = isBoolean
    }
}