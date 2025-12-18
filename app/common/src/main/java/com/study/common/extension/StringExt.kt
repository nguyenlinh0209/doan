package com.study.common.extension
fun String.parseAIResponse(): String {
    if (this.isBlank()) return ""

    var result = this

    // Remove markdown code blocks
    result = result.replace(Regex("```[a-zA-Z]*\\n"), "")
    result = result.replace(Regex("```"), "")

    // Remove markdown inline code
    result = result.replace(Regex("`([^`]+)`"), "$1")

    // Remove markdown bold and italic
    result = result.replace(Regex("\\*\\*([^*]+)\\*\\*"), "$1")
    result = result.replace(Regex("__([^_]+)__"), "$1")
    result = result.replace(Regex("\\*([^*]+)\\*"), "$1")
    result = result.replace(Regex("_([^_]+)_"), "$1")

    // Remove markdown headers
    result = result.replace(Regex("^#+\\s+"), "")

    // Remove markdown links [text](url) -> text
    result = result.replace(Regex("\\[([^\\]]+)\\]\\([^)]+\\)"), "$1")

    // Remove markdown lists
    result = result.replace(Regex("^[\\s]*[-*+]\\s+"), "")

    // Clean extra whitespace
    result = result.trim()
    result = result.replace(Regex("\\n\\n+"), "\n")

    return result
}