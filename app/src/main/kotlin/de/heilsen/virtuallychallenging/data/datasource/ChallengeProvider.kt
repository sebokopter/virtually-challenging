package de.heilsen.virtuallychallenging.data.datasource

import de.heilsen.virtuallychallenging.domain.model.Challenge

interface ChallengeProvider {
    fun get(): Challenge
    fun set(challenge: Challenge)
}