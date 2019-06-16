package com.example.testapp.data.net.dto

class BasePaginationListResponse<T> : BaseResponse<List<T>>() {
    val page: Int? = null
    val per_page: Int? = null
    val total: Int? = null
    val total_pages: Int? = null
}