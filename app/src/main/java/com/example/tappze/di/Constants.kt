package com.example.tappze.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Constants {
    @Provides
    @Singleton
    fun providesSocialAppNames() = arrayOf(
        Pair("Whatsapp", ""),
        Pair("Calendly", ""),
        Pair("Email" , "",),
        Pair("Facebook" , ""),
        Pair("Instagram" , ""),
        Pair("Linkedin" , ""),
        Pair("Paypal" , ""),
        Pair("Phone" , ""),
        Pair("Pinterest" , ""),
        Pair("Snapchat" , ""),
        Pair("Spotify" , ""),
        Pair("Telegram" , ""),
        Pair("Tiktok" , ""),
        Pair("Twitter" , ""),
        Pair("Website" , ""),
        Pair("Youtube" , "")
    )

    @Provides
    @Singleton
    fun providesSocialAppHelp() = mapOf(
        "Whatsapp" to 
                "Add you phone number including your country code (e.g. +6581234567).",
        "Calendly" to 
                "1. Open up your Calendly app and log into your account.\n" +
                "2. Copy the URL link below your name.\n" +
                "3. Paste the copied link into the Calendly URL field.",
        "Email" to 
                "Input your email address.",
        "Facebook" to 
                "If you are using the Facebook app on a mobile phone.\n" +
                "1. Open up your Facebook app and log into you Facebook account.\n" +
                "2. From the home page, click on the menu icon at the bottom right corner (It looks like three horizontal lines).\n" +
                "3. Click on your profile picture to go to your profile page.\n" +
                "4. Click on the Profile settings tab (three dots).\n" +
                "5. Click on Copy Link to copy your full Facebook profile link.\n" +
                "6. Paste the link into the Facebook URL field (e.g. www.facebook.com/your_fb_id).",
        "Instagram" to 
                "1. Open up your Instagram app and log into your account.\n" +
                "2. Click on your profile picture at the bottom right corner.\n" +
                "3. Your username will be shown at the very top of your profile (above your profile picture)\n" +
                "4. Paste your username into the Instagram URL field",
        "Linkedin" to 
                "If you are using the Linkedin app on a mobile phone.\n" +
                "1. Open your Linkedin app and log into your account\n" +
                "2. From the home page, click on the profile picture in the top left.\n" +
                "3. Click on the right side menu button (three dots).\n" +
                "4. Select share via...\n" +
                "5. Select Copy.\n" +
                "6. Paste the copied link into the Linkedin URL field.",
        "Paypal" to 
                "1. Go to www.paypal.com and log in.\n" +
                "2. Under your profile, select Get paypal.me\n" +
                "3. Create a paypal.me profile.\n" +
                "4. Copy your created paypal.me link and paste into the Paypal URL field.",
        "Phone" to 
                "Add you phone number including your country code (e.g. +6581234567).",
        "Pinterest" to
                "1. Open up your Pinterest app and log into your account.\n" +
                "2. Click on your profile picture at the bottom right corner.\n" +
                "3. Click on the three dots located at the top right corner.\n" +
                "4. Select copy profile link\n" +
                "5.  Paste the copied link into the Pinterest URL field.",
        "Snapchat" to
                "1. Open up your Snapchat app and log into your account.\n" +
                "2. Tap on your profile icon at the top left corner.\n" +
                "3. Your username is shown right next to your snapchat score.\n" +
                "4. Copy and paste the username into the Snapchat URL field.",
        "Spotify" to 
                "1. Search for your favourite Artist or Albums.\n" +
                "2. Click on the three dots menu selection and select share.\n" +
                "3. Select copy link.\n" +
                "4. Pate the copied link into Spotify URL field.",
        "Telegram" to
                "1. Open up your telegram app and log into your account.\n" +
                "2. Click on settings located at the bottom right corner.\n" +
                "3. Your username is shown under your contact number.\n" +
                "4. Copy and paste the username into the Telegram URL field.",
        "Tiktok" to
                "1. Open up your Tiktok app and log into your account.\n" +
                "2. Click on your profile icon at the bottom right corner.\n" +
                "3. Your username will be shown under your profile picture.\n" +
                "4. Copy and paste the username into the Tiktok URL field.",
        "Website" to
                "Enter the link to you website.",
        "Youtube" to
                "1. Sign in to you Youtube Studio account.\n" +
                "2. From the Menu, select Customization Basic Info.\n" +
                "3. Click the channel URL and copy the link\n" +
                "4. Paste the copied link into the Youtube URL field.",
    ) 
}