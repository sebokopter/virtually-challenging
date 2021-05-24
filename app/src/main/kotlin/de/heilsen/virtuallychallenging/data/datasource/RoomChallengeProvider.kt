package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.data.model.ChallengeDao
import de.heilsen.virtuallychallenging.data.model.ChallengeEntity

class RoomChallengeProvider(private val challengeDao: ChallengeDao) : ChallengeProvider {
    override fun get(): de.heilsen.virtuallychallenging.domain.model.Challenge {
        val challengeData = challengeDao.get()
        return de.heilsen.virtuallychallenging.domain.model.Challenge(challengeData.goal)
    }

    override fun set(challenge: de.heilsen.virtuallychallenging.domain.model.Challenge) {
        val challengeData = ChallengeEntity(challenge.goal)
        challengeDao.insert(challengeData)
    }
}