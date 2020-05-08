package com.example.avcapp.settings

class ProfileCard(cardTitle: String, cardValue: String, cardImage: Int) {

    private var title: String = cardTitle
    private var value: String = cardValue
    private var image: Int = cardImage


    fun getTitle(): String {
        return title
    }

    fun getProfileValue(): String {
        return value
    }

    fun getImage(): Int {
        return image
    }

    fun setTitle(cardTitle: String) {
        title = cardTitle
    }

    fun setProfileValue(cardValue: String) {
        value = cardValue
    }

    fun setImage(cardImage: Int) {
        image = cardImage
    }

}