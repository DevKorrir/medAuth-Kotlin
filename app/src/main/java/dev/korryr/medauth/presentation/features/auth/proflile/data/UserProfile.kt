package dev.korryr.medauth.presentation.features.auth.proflile.data


data class UserProfile(
    val name: String = "johndoe",
    val email: String = "doe.johny@medauth.com",
    val profileImageUrl: String = "",
    val verificationsCount: Int = 127,
    val scanCount: Int = 89,
    val joinDate: String = "March 2024"
)

//data class SettingItem(
//    val title: String,
//    val subtitle: String,
//    val icon: ImageVector,
//    val hasSwitch: Boolean = false,
//    val switchState: Boolean = false,
//    val onClick: () -> Unit = {}
//)