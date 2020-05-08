package com.example.avcapp

class EmergencyContactsCard(contactImage: Int, contactName: String?, contactNumber: String) {

    private var image: Int = contactImage
    private var name: String? = contactName
    private var number: String = contactNumber

    fun getImage(): Int {
        return image
    }

    fun getName(): String? {
        return name
    }

    fun getNumber(): String {
        return number
    }

    fun setImage(cardImage: Int) {
        image = cardImage
    }

    fun setName(contactName: String) {
        name = contactName
    }

    fun setNumber(contactNumber: String) {
        number = contactNumber
    }

}