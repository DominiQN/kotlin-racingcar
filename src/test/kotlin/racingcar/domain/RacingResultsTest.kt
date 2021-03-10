package racingcar.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import racingcar.domain.car.CarLocation
import racingcar.domain.car.CarName

internal class RacingResultsTest {
    private val dummyRecords = listOf(Record(CarName(""), CarLocation.ZERO))

    @ParameterizedTest
    @ValueSource(ints = [1, 10, 50])
    fun `trials는 TrialOrder의 오름차순으로 정렬된다`(numberOfTrials: Int) {
        val shuffledTrials = (1..numberOfTrials)
            .map { RacingTrial(TrialOrder(it), dummyRecords) }
            .shuffled()

        assertThat(RacingResults(shuffledTrials).trials)
            .containsExactlyElementsOf((1..numberOfTrials).map { RacingTrial(TrialOrder(it), dummyRecords) })
    }

    @Test
    fun `마지막 trial의 leadRecords를 가져온다`() {
        val results = RacingResults(
            listOf(
                makeDummy(order = 1, 초반우세 = 1, 중반우세 = 0, 최종우승 = 0),
                makeDummy(order = 2, 초반우세 = 1, 중반우세 = 1, 최종우승 = 1),
                makeDummy(order = 3, 초반우세 = 1, 중반우세 = 2, 최종우승 = 1),
                makeDummy(order = 4, 초반우세 = 1, 중반우세 = 2, 최종우승 = 2),
                makeDummy(order = 5, 초반우세 = 1, 중반우세 = 2, 최종우승 = 3)
            )
        )
        assertThat(results.winnerRecords)
            .containsExactly(Record("최종우승", 3))
    }

    private fun makeDummy(order: Int, 초반우세: Int, 중반우세: Int, 최종우승: Int) = RacingTrial(
        TrialOrder(order),
        listOf(
            Record("초반우세", 초반우세),
            Record("중반우세", 중반우세),
            Record("최종우승", 최종우승)
        )
    )
}
