package com.study.common.util

object PromptUtils {

    private val workKeywords = listOf(
        "task", "work", "job", "project", "todo",
        "assignment", "deadline", "due", "plan"
    )

    fun getPrompt(input: String, count: Int): String {
        val lowerInput = input.lowercase()

        val isWorkRelated = workKeywords.any { keyword ->
            lowerInput.contains(keyword)
        }

        return if (isWorkRelated) {
            """
You are a professional AI task management assistant.
Analyze and summarize the following task or work-related information in a clear, detailed paragraph format:

$input

Your summary should include:
- Task urgency level and priority assessment
- Current status and progress
- Key dates (creation date, start date, due date if available)
- Important tags or categories
- Assignment details
- Any attachments or additional information
- Recommended next steps

Write in a natural, flowing paragraph style.
            """.trimIndent()
        } else {
            // 🔥 FLASHCARD MODE
            """
You are an AI that creates study flashcards.

Topic:
$input

Create exactly $count flashcards.

Rules:
- Each flashcard must have a FRONT and a BACK
- Keep content concise, clear, and suitable for studying
- Do NOT add explanations outside flashcards
- Output format MUST be exactly like this:

FLASHCARD 1
FRONT: ...
BACK: ...

FLASHCARD 2
FRONT: ...
BACK: ...

(continue until FLASHCARD $count)

Important:
- Do not skip numbers
- Do not add extra text before or after
            """.trimIndent()
        }
    }

    fun getQuizPrompt(input: String, count: Int): String {
        return """
You are an AI that creates multiple-choice quiz questions.

Topic:
$input

Create exactly $count quiz questions.

Rules:
- Each question MUST have:
  - QUESTION
  - OPTION A
  - OPTION B
  - OPTION C
  - OPTION D
  - ANSWER (only A, B, C, or D)
  - EXPLAIN (short explanation why the answer is correct)

- Output format MUST be EXACTLY like this:

QUESTION 1:
CONTENT: ...
A: ...
B: ...
C: ...
D: ...
ANSWER: A
EXPLAIN: ...

QUESTION 2:
CONTENT: ...
A: ...
B: ...
C: ...
D: ...
ANSWER: C
EXPLAIN: ...

(continue until QUESTION $count)

Important:
- Do NOT add any extra text
- Do NOT change labels
- Keep explanations concise
        """.trimIndent()
    }
}
