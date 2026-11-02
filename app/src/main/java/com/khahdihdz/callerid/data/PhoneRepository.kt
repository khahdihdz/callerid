package com.khahdihdz.callerid.data

class PhoneRepository(private val dao: PhoneDao) {
    suspend fun search(raw: String): List<PhoneNumberEntity> {
        val query = raw.trim()
        if (query.isEmpty()) return emptyList()
        return dao.search(query)
    }

    suspend fun lookup(raw: String): PhoneNumberEntity? {
        val number = normalize(raw) ?: return null
        return dao.find(number)
    }

    suspend fun add(number: String, name: String?, category: String, spamScore: Int) {
        val normalized = normalize(number) ?: return
        dao.upsert(PhoneNumberEntity(number = normalized, displayName = name?.trim(), category = category, spamScore = spamScore.coerceIn(0, 100)))
    }

    private fun normalize(raw: String): String? {
        val value = raw.trim().replace(Regex("[\\s().-]"), "")
        return value.takeIf { it.length in 7..16 && it.matches(Regex("^\\+?[0-9]+$")) }
    }
}
