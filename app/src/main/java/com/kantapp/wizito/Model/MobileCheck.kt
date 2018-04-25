package com.kantapp.wizito.Model

/**
 * Created by Android A on 4/20/2018.
 */

data class MobileCheck(
		val error: Boolean,
		val message: String,
		val visitor: Visitor
)

data class Visitor(
		val id: Int,
		val name: String,
		val mobile: String,
		val email: String,
		val company: String
)