package com.tomeiru.birthday_reminder.batch_import

import android.os.Parcelable
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

//TODO: reset to yes and no instead of Yes and No
class BirthdayCSV {
    companion object {
        const val LINE_SEPARATOR = "\r\n"
        const val CELL_SEPARATOR = ','
        const val HEADER = "name,date,celebrated"
    }

    @Parcelize
    data class ValidationOutput(
        val correctLines: List<Int> = listOf(),
        val wrongLines: List<Int> = listOf(),
        val isValid: Boolean = false,
        val fileContent: String = ""
    ) : Parcelable

    private fun validateLine(line: String): Boolean {
        val cells = line.split(CELL_SEPARATOR)
        if (cells.size != 3) return false
        if (cells[0].trim().isEmpty()) return false
        val celebrated = cells[2].trim()
        if (celebrated != "Yes" && celebrated != "No") return false
        val date = cells[1].split('-')
        if (date.size != 2 && date.size != 3) return false
        try {
            if (date.size == 2) MonthDay.parse("--${cells[1]}")
            else LocalDate.parse(cells[1])
        } catch (e: DateTimeParseException) {
            return false
        }
        return true
    }

    fun validateFormat(fileContent: String): ValidationOutput {
        val lines = fileContent.split(LINE_SEPARATOR);
        val correctLines = mutableListOf<Int>()
        val wrongLines = mutableListOf<Int>()

        if (lines.isEmpty() || lines.size == 1) return ValidationOutput()
        for ((index, line) in lines.withIndex()) {
            if (index == 0 || line.isEmpty()) continue
            if (validateLine(line)) correctLines.add(index + 1)
            else wrongLines.add(index + 1)
        }
        return ValidationOutput(
            correctLines,
            wrongLines,
            correctLines.size != 0,
            fileContent
        )
    }

    private fun parseLine(line: String): Birthday {
        val values = line.split(CELL_SEPARATOR)
        val dateParts = values[1].split('-')
        val celebrated = values[2] == "Yes"
        if (dateParts.size == 2)
            return Birthday(values[0], MonthDay.parse("--${values[1]}"), celebrated)
        return Birthday(values[0], LocalDate.parse(values[1]), celebrated)
    }

    fun parseFormat(validationOutput: ValidationOutput): List<Birthday> {
        val lines = validationOutput.fileContent.split(LINE_SEPARATOR)
            .filterIndexed { index, line ->
                !(index == 0 || validationOutput.wrongLines.contains(index + 1) || line.isEmpty())
            }
        return lines.map { line -> parseLine(line) }
    }

    private fun birthdayToFormatLine(birthday: Birthday): String {
        val formattedDate = if (birthday.year == null) MonthDay.of(birthday.month, birthday.day)
            .format(DateTimeFormatter.ofPattern("y-MM"))
        else LocalDate.of(birthday.year, birthday.month, birthday.day)
            .format(DateTimeFormatter.ISO_LOCAL_DATE)

        return "${birthday.name}${CELL_SEPARATOR}${formattedDate}${CELL_SEPARATOR}\$${if (birthday.celebrated) "yes" else "no"}"
    }

    fun birthdaysToFormat(birthdays: List<Birthday>): String {
        var csv: String = HEADER
        for (birthday in birthdays) {
            csv = csv + LINE_SEPARATOR + birthdayToFormatLine(birthday)
        }
        return csv
    }
}