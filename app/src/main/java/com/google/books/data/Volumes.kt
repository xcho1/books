package com.google.books.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Volumes(@SerializedName("kinds") val kinds: String,
                   @SerializedName("totalItems") val totalItems: Int,
                   @SerializedName("items") val items: List<Item>) {

    data class Item(@SerializedName("kind") val kind: String,
                    @SerializedName("id") val id: String,
                    @SerializedName("etag") val etag: String,
                    @SerializedName("selfLink") val selfLink: String,
                    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo,
                    @SerializedName("saleInfo") val saleInfo: SaleInfo,
                    @SerializedName("accessInfo") val accessInfo: AccessInfo,
                    @SerializedName("searchInfo") val searchInfo: SearchInfo
    ) : Serializable

    data class VolumeInfo(@SerializedName("title") val title: String,
                          @SerializedName("subtitle") val subtitle: String,
                          @SerializedName("authors") val authors: List<String>?,
                          @SerializedName("publisher") val publisher: String?,
                          @SerializedName("publishedDate") val publishedDate: String,
                          @SerializedName("description") val description: String?,
                          @SerializedName("industryIdentifiers") val industryIdentifiers: List<IndustryIdentifier>,
                          @SerializedName("readingModes") val readingModes: ReadingModes,
                          @SerializedName("pageCount") val pageCount: Int,
                          @SerializedName("printType") val printType: String,
                          @SerializedName("categories") val categories: List<String>?,
                          @SerializedName("averageRating") val averageRating: Double?,
                          @SerializedName("ratingCount") val ratingCount: Int?,
                          @SerializedName("maturityRating") val maturityRating: String,
                          @SerializedName("allowAnonLogging") val allowAnonLogging: Boolean,
                          @SerializedName("contentVersion") val contentVersion: String,
                          @SerializedName("panelizationSummary") val panelizationSummary: PanelizationSummary,
                          @SerializedName("imageLinks") val imageLinks: ImageLinks?,
                          @SerializedName("languange") val language: String,
                          @SerializedName("perviewLinke") val previewLink: String,
                          @SerializedName("infoLink") val infoLink: String,
                          @SerializedName("canonicalVolumeLink") val canonicalVolumeLink: String) : Serializable

    data class IndustryIdentifier(@SerializedName("type") val type: String,
                                  @SerializedName("identifier") val identifier: String) : Serializable

    data class ReadingModes(@SerializedName("text") val text: Boolean,
                            @SerializedName("image") val image: Boolean) : Serializable

    data class PanelizationSummary(@SerializedName("containsEpubBubbles") val containsEpubBubbles: Boolean,
                                   @SerializedName("containsImageBubbles") val containsImageBubbles: Boolean) : Serializable

    data class ImageLinks(@SerializedName("smallThumbnail") val smallThumbnail: String,
                          @SerializedName("thumbnail") val thumbnail: String) : Serializable

    data class SaleInfo(@SerializedName("country") val country: String,
                        @SerializedName("saleability") val saleability: String,
                        @SerializedName("isEbook") val isEbook: Boolean) : Serializable

    data class AccessInfo(@SerializedName("country") val country: String,
                          @SerializedName("viewability") val viewability: String,
                          @SerializedName("embeddable") val embeddable: Boolean,
                          @SerializedName("publicDomain") val publicDomain: Boolean,
                          @SerializedName("textToSpeechPermission") val textToSpeechPermission: String,
                          @SerializedName("epub") val epub: Availability,
                          @SerializedName("pdf") val pdf: Availability,
                          @SerializedName("webReaderLink") val webReaderLink: String,
                          @SerializedName("accessViewStatus") val accessViewStatus: String,
                          @SerializedName("quoteSharingAllowed") val quoteSharingAllowed: Boolean) : Serializable

    data class Availability(@SerializedName("isAvailable") val isAvailable: Boolean) : Serializable

    data class SearchInfo(@SerializedName("textSnippet") val textSnippet: String) : Serializable
}