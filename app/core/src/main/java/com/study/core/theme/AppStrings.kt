package com.study.core.theme

/**
 * Centralized string resources for the entire app
 * Replaces strings.xml with a Kotlin object for type safety
 * Usage: AppStrings.appName, AppStrings.home
 */
object AppStrings {

    // ============ APP NAME & MAIN ============
    const val appName = "Study"
    const val home = "Home"
    const val lesson = "Lesson"
    const val create = "Create"
    const val myStudio = "My Studio"
    const val recent = "Recent"
    const val myFavourite = "My Favourite"
    const val calender = "Calender"
    const val ggdocs = "Docs"
    const val chat = "Chat"
    const val buildingSpace = "Building Space"
    const val myWork = "My Work"
    const val date = "Date"

    // ============ MAIN ACTIONS ============
    const val addPerson = "Add Person"
    const val personName = "Person Name"
    const val required = "Required"

    // ============ INTRO SCREEN ============
    const val skip = "Skip"
    const val getStarted = "Get Started"
    const val continueTitle = "Continue"

    // ============ COMMON STRINGS ============
    const val crop = "Crop"
    const val selectMediaSource = "Select Media Source"
    const val camera = "Camera"
    const val gallery = "Gallery"
    const val solve = "Solve"
    const val retake = "Retake"
    const val rotate = "Rotate"
    const val cameraAccessIsDenied = "Camera access is denied"
    const val pleaseEnableCameraPermission =
        "Please enable camera permission in Settings to continue"
    const val permission = "Permission"
    const val recognizingText = "Recognizing text…"
    const val noTextFound = "There is no text found in the image"
    const val pleaseEnableStoragePermission =
        "Please enable the read and write external storage to use this app"
    const val requiredField = "This is a required field"
    const val today = "Today"
    const val yesterday = "Yesterday"
    const val addCategory = "Add Category"
    const val enterNameHere = "Enter name here"
    const val edit = "Edit"
    const val seeAll = "See all"
    const val thanksForFeedback = "Thanks for your feedback!"
    const val deleteConfirmation =
        "Are you sure you want to delete these entries? This action cannot be undone."
    const val select = "Select"
    const val selectAll = "Select all"
    const val editFolder = "Edit folder"
    const val rename = "Rename"
    const val search = "Search"
    const val recentSearch = "Recent Search"

    // ============ FILE FORMATS ============
    const val png = "PNG"
    const val jpg = "JPG"
    const val psd = "PSD"
    const val jepg = "JEPG"
    const val pdf = "PDF"
    const val tiff = "TIFF"

    // ============ FILE OPERATIONS ============
    const val duplicate = "Duplicate"
    const val move = "Move"
    const val export = "Export"
    const val activity = "Activity"
    const val notification = "Notification"
    const val selectItemToMove = "Select item you want to move"
    const val selectCategory = "Select Category"
    const val willBeMovedToTrash =
        "It will be moved to the Trash and you can restore it anytime."
    const val deleteThisItem = "Delete This Item?"
    const val shareAs = "Share As"
    const val exportDraw = "Export Draw"
    const val managerCategories = "Manager Categories"
    const val longPressAndDragToReorder = "Long press and drag to reorder"
    const val favorite = "Favorite"
    const val unFavorite = "Un Favorite"
    const val myDrawings = "My Drawings"
    const val share = "Share"
    const val editTitle = "Edit Title"
    const val tranducvu = "TranDucVuSpace"
    const val v = "V"
    const val searchHint = "Search Hint"

    // ============ COLLABORATION ============
    const val assignedToMe = "Assigned to me"
    const val comments = "Comments"
    const val canlendar = "canlendar"
    const val directMesssage = "Direct Messsage"
    const val pinned = "Pinned"
    const val channels = "Channels"

    // ============ AUTHENTICATION ============
    const val signIn = "Sign in"
    const val signUp = "Sign Up"
    const val email = "Email"
    const val fullName = "Full Name"
    const val password = "Password"
    const val forgotPassword = "Forgot Password?"
    const val orConnectUsing = "Or connect using"

    // ============ TASK MANAGEMENT ============
    const val details = "Details"
    const val description = "Description"
    const val statusAndType = "Status & Type"
    const val addDates = "Add Dates"
    const val subtasks = "Subtasks"
    const val addProperty = "Add property"
    const val todo = "TODO"
    const val inProgress = "IN PROGRESS"
    const val complete = "COMPLETE"
    const val done = "Done"
    const val cancel = "Cancel"
    const val addTime = "+ Add Time"
    const val startDate = "Start date"
    const val dueDate = "Due date"
    const val tomorrow = "Tomorrow"
    const val nextWeek = "Next week"
    const val thisWeekend = "This weekend"
    const val close = "Close"
    const val selectTime = "Select Time"
    const val hour = "Hour"
    const val minute = "Minute"
    const val addDescription = "Add description"
    const val enterTextHere = "Enter text here"
    const val addAttachment = "Add Attachment"
    const val file = "File"
    const val enterDescription = "Enter description"
    const val checkList = "Check List"

    // ============ PRIORITY LEVELS ============
    const val low = "Low"
    const val normal = "Normal"
    const val high = "High"
    const val priority = "Priority"

    // ============ FORMATTING ============
    const val font = "Font"
    const val timeFormatterHour = "%02d"
    const val timeFormatterMinute = "%02d"
    const val charCount = "%1\$d/%2\$d"

    // ============ CHECKLIST ============
    const val addFirstChecklistItem = "Tap to add the first item"
    const val newChecklistItem = "Enter new item..."
    const val addItem = "Add item"
    const val addTask = "Add Task"
    const val taskTitle = "Task Title"
    const val createTask = "Create Task"

    // ============ AI & FEATURES ============
    const val aiAssistant = "AI Assistant"
    const val findEmail = "Find Email"

    // ============ PROFILE & SETTINGS ============
    const val profileAndSettings = "Profile & Settings"
    const val editProfile = "Edit Profile"
    const val items = "Items"
    const val yourSavedItems = "Your saved items and files"
    const val listFriend = "List Friend"
    const val searchByEmail = "Search by Email"
    const val searchResults = "Search Results"
    const val chatHistoryBeginning =
        "This is very beginning of your Chat history.\n Only the two of you are in this convention"
    const val personalList = "Personal List"
    const val myHome = "My Home"
    const val settings = "settings"

    // ============ DEVICE MANAGEMENT ============
    const val scan = "Scan"
    const val noDevice = "No Device"
    const val addDevice = "Add Device"

    // ============ COMMON BUTTONS ============
    const val ok = "OK"
}

// ============ EXTENSION FUNCTION FOR STRING FORMATTING ============

fun String.formatString(vararg args: Any): String {
    return try {
        String.format(this, *args)
    } catch (e: Exception) {
        this
    }
}

// ============ STRING CATEGORIES (For Better Organization) ============
object AppStringCategories {

    object Navigation {
        const val home = AppStrings.home
        const val lesson = AppStrings.lesson
        const val create = AppStrings.create
        const val myStudio = AppStrings.myStudio
        const val chat = AppStrings.chat
    }

    object Intro {
        const val skip = AppStrings.skip
        const val getStarted = AppStrings.getStarted
        const val continueTitle = AppStrings.continueTitle
    }

    object MediaSource {
        const val selectMediaSource = AppStrings.selectMediaSource
        const val camera = AppStrings.camera
        const val gallery = AppStrings.gallery
    }

    object ImageEditing {
        const val crop = AppStrings.crop
        const val rotate = AppStrings.rotate
        const val retake = AppStrings.retake
        const val solve = AppStrings.solve
    }

    object FileFormats {
        const val png = AppStrings.png
        const val jpg = AppStrings.jpg
        const val psd = AppStrings.psd
        const val pdf = AppStrings.pdf
        const val tiff = AppStrings.tiff
    }

    object FileOperations {
        const val duplicate = AppStrings.duplicate
        const val move = AppStrings.move
        const val export = AppStrings.export
        const val delete = AppStrings.deleteThisItem
        const val share = AppStrings.share
    }

    object Authentication {
        const val signIn = AppStrings.signIn
        const val signUp = AppStrings.signUp
        const val email = AppStrings.email
        const val password = AppStrings.password
        const val forgotPassword = AppStrings.forgotPassword
    }

    object Task {
        const val taskTitle = AppStrings.taskTitle
        const val description = AppStrings.description
        const val status = AppStrings.statusAndType
        const val priority = AppStrings.priority
        const val addTask = AppStrings.addTask
        const val createTask = AppStrings.createTask
    }

    object Priority {
        const val low = AppStrings.low
        const val normal = AppStrings.normal
        const val high = AppStrings.high
    }

    object Status {
        const val todo = AppStrings.todo
        const val inProgress = AppStrings.inProgress
        const val complete = AppStrings.complete
        const val done = AppStrings.done
    }

    object Dates {
        const val today = AppStrings.today
        const val yesterday = AppStrings.yesterday
        const val tomorrow = AppStrings.tomorrow
        const val nextWeek = AppStrings.nextWeek
        const val thisWeekend = AppStrings.thisWeekend
    }

    object Errors {
        const val cameraAccessDenied = AppStrings.cameraAccessIsDenied
        const val cameraPermission = AppStrings.pleaseEnableCameraPermission
        const val storagePermission = AppStrings.pleaseEnableStoragePermission
        const val requiredField = AppStrings.requiredField
        const val noTextFound = AppStrings.noTextFound
    }

    object Common {
        const val ok = AppStrings.ok
        const val cancel = AppStrings.cancel
        const val close = AppStrings.close
        const val edit = AppStrings.edit
        const val select = AppStrings.select
        const val search = AppStrings.search
    }
}
