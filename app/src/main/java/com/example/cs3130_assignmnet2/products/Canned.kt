package com.example.cs3130_assignmnet2

data class Canned(
    override var name: String,
    override var description: String,
    override var sku: String,
    override var price: Float,
    override var image: String
) : Product()

