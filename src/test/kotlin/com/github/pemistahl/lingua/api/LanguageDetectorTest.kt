/*
 * Copyright 2018-2019 Peter M. Stahl pemistahl@googlemail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pemistahl.lingua.api

import com.github.pemistahl.lingua.api.Language.AFRIKAANS
import com.github.pemistahl.lingua.api.Language.ALBANIAN
import com.github.pemistahl.lingua.api.Language.ARABIC
import com.github.pemistahl.lingua.api.Language.BASQUE
import com.github.pemistahl.lingua.api.Language.BELARUSIAN
import com.github.pemistahl.lingua.api.Language.BULGARIAN
import com.github.pemistahl.lingua.api.Language.CATALAN
import com.github.pemistahl.lingua.api.Language.CROATIAN
import com.github.pemistahl.lingua.api.Language.CZECH
import com.github.pemistahl.lingua.api.Language.DANISH
import com.github.pemistahl.lingua.api.Language.DUTCH
import com.github.pemistahl.lingua.api.Language.ENGLISH
import com.github.pemistahl.lingua.api.Language.ESTONIAN
import com.github.pemistahl.lingua.api.Language.FINNISH
import com.github.pemistahl.lingua.api.Language.FRENCH
import com.github.pemistahl.lingua.api.Language.GERMAN
import com.github.pemistahl.lingua.api.Language.GREEK
import com.github.pemistahl.lingua.api.Language.HUNGARIAN
import com.github.pemistahl.lingua.api.Language.ICELANDIC
import com.github.pemistahl.lingua.api.Language.INDONESIAN
import com.github.pemistahl.lingua.api.Language.IRISH
import com.github.pemistahl.lingua.api.Language.ITALIAN
import com.github.pemistahl.lingua.api.Language.LATIN
import com.github.pemistahl.lingua.api.Language.LATVIAN
import com.github.pemistahl.lingua.api.Language.LITHUANIAN
import com.github.pemistahl.lingua.api.Language.MALAY
import com.github.pemistahl.lingua.api.Language.NORWEGIAN
import com.github.pemistahl.lingua.api.Language.PERSIAN
import com.github.pemistahl.lingua.api.Language.POLISH
import com.github.pemistahl.lingua.api.Language.PORTUGUESE
import com.github.pemistahl.lingua.api.Language.ROMANIAN
import com.github.pemistahl.lingua.api.Language.RUSSIAN
import com.github.pemistahl.lingua.api.Language.SLOVAK
import com.github.pemistahl.lingua.api.Language.SLOVENE
import com.github.pemistahl.lingua.api.Language.SOMALI
import com.github.pemistahl.lingua.api.Language.SPANISH
import com.github.pemistahl.lingua.api.Language.SWEDISH
import com.github.pemistahl.lingua.api.Language.TAGALOG
import com.github.pemistahl.lingua.api.Language.TURKISH
import com.github.pemistahl.lingua.api.Language.UNKNOWN
import com.github.pemistahl.lingua.api.Language.VIETNAMESE
import com.github.pemistahl.lingua.api.Language.WELSH
import com.github.pemistahl.lingua.internal.model.Bigram
import com.github.pemistahl.lingua.internal.model.Fivegram
import com.github.pemistahl.lingua.internal.model.LanguageModel
import com.github.pemistahl.lingua.internal.model.Ngram
import com.github.pemistahl.lingua.internal.model.Quadrigram
import com.github.pemistahl.lingua.internal.model.Trigram
import com.github.pemistahl.lingua.internal.model.Unigram
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(MockKExtension::class)
class LanguageDetectorTest {

    @MockK
    private lateinit var unigramLanguageModelForEnglish: LanguageModel<Unigram, Unigram>

    @MockK
    private lateinit var bigramLanguageModelForEnglish: LanguageModel<Bigram, Bigram>

    @MockK
    private lateinit var trigramLanguageModelForEnglish: LanguageModel<Trigram, Trigram>

    @MockK
    private lateinit var quadrigramLanguageModelForEnglish: LanguageModel<Quadrigram, Quadrigram>

    @MockK
    private lateinit var fivegramLanguageModelForEnglish: LanguageModel<Fivegram, Fivegram>

    @MockK
    private lateinit var unigramLanguageModelForGerman: LanguageModel<Unigram, Unigram>

    @MockK
    private lateinit var bigramLanguageModelForGerman: LanguageModel<Bigram, Bigram>

    @MockK
    private lateinit var trigramLanguageModelForGerman: LanguageModel<Trigram, Trigram>

    @MockK
    private lateinit var quadrigramLanguageModelForGerman: LanguageModel<Quadrigram, Quadrigram>

    @MockK
    private lateinit var fivegramLanguageModelForGerman: LanguageModel<Fivegram, Fivegram>

    @SpyK
    private var detectorForEnglishAndGerman = LanguageDetector(
        languages = mutableSetOf(ENGLISH, GERMAN),
        isCachedByMapDB = false
    )

    private val detectorForAllLanguages = LanguageDetector(
        languages = Language.all().toMutableSet(),
        isCachedByMapDB = false
    )

    @BeforeAll
    fun beforeAll() {
        // UNIGRAMS
        // English
        every { unigramLanguageModelForEnglish.getRelativeFrequency(Unigram("a")) } returns 0.01
        every { unigramLanguageModelForEnglish.getRelativeFrequency(Unigram("l")) } returns 0.02
        every { unigramLanguageModelForEnglish.getRelativeFrequency(Unigram("t")) } returns 0.03
        every { unigramLanguageModelForEnglish.getRelativeFrequency(Unigram("e")) } returns 0.04
        every { unigramLanguageModelForEnglish.getRelativeFrequency(Unigram("r")) } returns 0.05

        // German
        every { unigramLanguageModelForGerman.getRelativeFrequency(Unigram("a")) } returns 0.06
        every { unigramLanguageModelForGerman.getRelativeFrequency(Unigram("l")) } returns 0.07
        every { unigramLanguageModelForGerman.getRelativeFrequency(Unigram("t")) } returns 0.08
        every { unigramLanguageModelForGerman.getRelativeFrequency(Unigram("e")) } returns 0.09
        every { unigramLanguageModelForGerman.getRelativeFrequency(Unigram("r")) } returns 0.1

        // BIGRAMS
        // English
        every { bigramLanguageModelForEnglish.getRelativeFrequency(Bigram("al")) } returns 0.11
        every { bigramLanguageModelForEnglish.getRelativeFrequency(Bigram("lt")) } returns 0.12
        every { bigramLanguageModelForEnglish.getRelativeFrequency(Bigram("te")) } returns 0.13
        every { bigramLanguageModelForEnglish.getRelativeFrequency(Bigram("er")) } returns 0.14

        // German
        every { bigramLanguageModelForGerman.getRelativeFrequency(Bigram("al")) } returns 0.15
        every { bigramLanguageModelForGerman.getRelativeFrequency(Bigram("lt")) } returns 0.16
        every { bigramLanguageModelForGerman.getRelativeFrequency(Bigram("te")) } returns 0.17
        every { bigramLanguageModelForGerman.getRelativeFrequency(Bigram("er")) } returns 0.18

        // TRIGRAMS
        // English
        every { trigramLanguageModelForEnglish.getRelativeFrequency(Trigram("alt")) } returns 0.19
        every { trigramLanguageModelForEnglish.getRelativeFrequency(Trigram("lte")) } returns 0.2
        every { trigramLanguageModelForEnglish.getRelativeFrequency(Trigram("ter")) } returns 0.21

        // German
        every { trigramLanguageModelForGerman.getRelativeFrequency(Trigram("alt")) } returns 0.22
        every { trigramLanguageModelForGerman.getRelativeFrequency(Trigram("lte")) } returns 0.23
        every { trigramLanguageModelForGerman.getRelativeFrequency(Trigram("ter")) } returns 0.24

        // QUADRIGRAMS
        // English
        every { quadrigramLanguageModelForEnglish.getRelativeFrequency(Quadrigram("alte")) } returns 0.25
        every { quadrigramLanguageModelForEnglish.getRelativeFrequency(Quadrigram("lter")) } returns 0.26

        // German
        every { quadrigramLanguageModelForGerman.getRelativeFrequency(Quadrigram("alte")) } returns 0.27
        every { quadrigramLanguageModelForGerman.getRelativeFrequency(Quadrigram("lter")) } returns 0.28

        // FIVEGRAMS
        // English
        every { fivegramLanguageModelForEnglish.getRelativeFrequency(Fivegram("alter")) } returns 0.29

        // German
        every { fivegramLanguageModelForGerman.getRelativeFrequency(Fivegram("alter")) } returns 0.30

        with (detectorForEnglishAndGerman) {
            unigramLanguageModels[ENGLISH] = lazy { unigramLanguageModelForEnglish }
            unigramLanguageModels[GERMAN] = lazy { unigramLanguageModelForGerman }

            bigramLanguageModels[ENGLISH] = lazy { bigramLanguageModelForEnglish }
            bigramLanguageModels[GERMAN] = lazy { bigramLanguageModelForGerman }

            trigramLanguageModels[ENGLISH] = lazy { trigramLanguageModelForEnglish }
            trigramLanguageModels[GERMAN] = lazy { trigramLanguageModelForGerman }

            quadrigramLanguageModels[ENGLISH] = lazy { quadrigramLanguageModelForEnglish }
            quadrigramLanguageModels[GERMAN] = lazy { quadrigramLanguageModelForGerman }

            fivegramLanguageModels[ENGLISH] = lazy { fivegramLanguageModelForEnglish }
            fivegramLanguageModels[GERMAN] = lazy { fivegramLanguageModelForGerman }
        }
    }

    @Test
    fun `assert that strings without letters return unknown language`() {
        val invalidStrings = listOf("", " \n  \t;", "3<856%)§")
        assertThat(detectorForAllLanguages.detectLanguagesOf(invalidStrings)).allMatch { it == UNKNOWN }
    }

    @ParameterizedTest
    @MethodSource("identifiedLanguageProvider")
    fun `assert that language can be unambiguously identified with rules`(
        word: String,
        expectedLanguage: Language
    ) {
        assertThat(
            detectorForAllLanguages.detectLanguageWithRules(listOf(word))
        ).`as`(
            "word '$word'"
        ).isEqualTo(
            expectedLanguage
        )
    }

    @ParameterizedTest
    @MethodSource("filteredLanguagesProvider")
    fun `assert that languages can be correctly filtered by rules`(
        word: String,
        expectedLanguages: List<Language>
    ) {
        val unexpectedLanguages = Language.all().filterNot { it in expectedLanguages }
        val assertionErrorDescription = "word '$word'"

        detectorForAllLanguages.resetLanguageFilter()
        detectorForAllLanguages.filterLanguagesByRules(listOf(word))

        assertThat(
            expectedLanguages
        ).`as`(
            assertionErrorDescription
        ).allMatch {
            it.isExcludedFromDetection == false
        }

        assertThat(
            unexpectedLanguages
        ).`as`(
            assertionErrorDescription
        ).allMatch {
            it.isExcludedFromDetection == true
        }
    }

    @ParameterizedTest
    @MethodSource("ngramProbabilityProvider")
    internal fun `assert that ngram probability lookup works correctly`(
        language: Language,
        ngram: Ngram,
        expectedProbability: Double
    ) {
        assertThat(
            detectorForEnglishAndGerman.lookUpNgramProbability(language, ngram)
        ).`as`(
            "language '$language', ngram '$ngram'"
        ).isEqualTo(
            expectedProbability
        )
    }

    private fun identifiedLanguageProvider() = listOf(
        arguments("والموضوع", UNKNOWN),
        arguments("сопротивление", UNKNOWN),
        arguments("house", UNKNOWN),
        arguments("hashemidëve", ALBANIAN),
        arguments("substituïts", CATALAN),
        arguments("rozdělit", CZECH),
        arguments("tvořen", CZECH),
        arguments("subjektů", CZECH),
        arguments("groß", GERMAN),
        arguments("σχέδια", GREEK),
        arguments("fekvő", HUNGARIAN),
        arguments("meggyűrűzni", HUNGARIAN),
        arguments("aizklātā", LATVIAN),
        arguments("sistēmas", LATVIAN),
        arguments("teoloģiska", LATVIAN),
        arguments("palīdzi", LATVIAN),
        arguments("blaķene", LATVIAN),
        arguments("ceļojumiem", LATVIAN),
        arguments("numuriņu", LATVIAN),
        arguments("mergelės", LITHUANIAN),
        arguments("įrengus", LITHUANIAN),
        arguments("slegiamų", LITHUANIAN),
        arguments("zmieniły", POLISH),
        arguments("państwowych", POLISH),
        arguments("mniejszości", POLISH),
        arguments("groźne", POLISH),
        arguments("ialomiţa", ROMANIAN),
        arguments("podĺa", SLOVAK),
        arguments("pohľade", SLOVAK),
        arguments("mŕtvych", SLOVAK),
        arguments("mihrabın", TURKISH),
        arguments("uğramayan", TURKISH),
        arguments("cằm", VIETNAMESE),
        arguments("thần", VIETNAMESE),
        arguments("chẳng", VIETNAMESE),
        arguments("quẩy", VIETNAMESE),
        arguments("sẵn", VIETNAMESE),
        arguments("nhẫn", VIETNAMESE),
        arguments("dắt", VIETNAMESE),
        arguments("chất", VIETNAMESE),
        arguments("đạp", VIETNAMESE),
        arguments("mặn", VIETNAMESE),
        arguments("hậu", VIETNAMESE),
        arguments("hiền", VIETNAMESE),
        arguments("lẻn", VIETNAMESE),
        arguments("biểu", VIETNAMESE),
        arguments("kẽm", VIETNAMESE),
        arguments("diễm", VIETNAMESE),
        arguments("phế", VIETNAMESE),
        arguments("nhẹn", VIETNAMESE),
        arguments("việc", VIETNAMESE),
        arguments("chỉnh", VIETNAMESE),
        arguments("trĩ", VIETNAMESE),
        arguments("ravị", VIETNAMESE),
        arguments("thơ", VIETNAMESE),
        arguments("nguồn", VIETNAMESE),
        arguments("thờ", VIETNAMESE),
        arguments("sỏi", VIETNAMESE),
        arguments("tổng", VIETNAMESE),
        arguments("nhở", VIETNAMESE),
        arguments("mỗi", VIETNAMESE),
        arguments("bỡi", VIETNAMESE),
        arguments("tốt", VIETNAMESE),
        arguments("giới", VIETNAMESE),
        arguments("chọn", VIETNAMESE),
        arguments("một", VIETNAMESE),
        arguments("hợp", VIETNAMESE),
        arguments("hưng", VIETNAMESE),
        arguments("từng", VIETNAMESE),
        arguments("của", VIETNAMESE),
        arguments("sử", VIETNAMESE),
        arguments("cũng", VIETNAMESE),
        arguments("những", VIETNAMESE),
        arguments("chức", VIETNAMESE),
        arguments("dụng", VIETNAMESE),
        arguments("thực", VIETNAMESE),
        arguments("kỳ", VIETNAMESE),
        arguments("kỷ", VIETNAMESE),
        arguments("mỹ", VIETNAMESE),
        arguments("mỵ", VIETNAMESE)
    )

    private fun filteredLanguagesProvider() = listOf(
        arguments("والموضوع", listOf(ARABIC, PERSIAN)),
        arguments("сопротивление", listOf(BELARUSIAN, BULGARIAN, RUSSIAN)),
        arguments("prihvaćanju", listOf(CROATIAN, POLISH)),
        arguments("nađete", listOf(CROATIAN, VIETNAMESE)),
        arguments("visão", listOf(PORTUGUESE, VIETNAMESE)),
        arguments("wystąpią", listOf(LITHUANIAN, POLISH)),
        arguments("budowę", listOf(LITHUANIAN, POLISH)),
        arguments("nebūsime", listOf(LATVIAN, LITHUANIAN)),
        arguments("afişate", listOf(ROMANIAN, TURKISH)),
        arguments("kradzieżami", listOf(POLISH, ROMANIAN)),
        arguments("înviat", listOf(FRENCH, ROMANIAN)),
        arguments("venerdì", listOf(ITALIAN, VIETNAMESE)),
        arguments("años", listOf(BASQUE, SPANISH)),
        arguments("rozohňuje", listOf(CZECH, SLOVAK)),
        arguments("rtuť", listOf(CZECH, SLOVAK)),
        arguments("pregătire", listOf(ROMANIAN, VIETNAMESE)),
        arguments("jeďte", listOf(CZECH, ROMANIAN, SLOVAK)),
        arguments("minjaverðir", listOf(ICELANDIC, LATVIAN, TURKISH)),
        arguments("þagnarskyldu", listOf(ICELANDIC, LATVIAN, TURKISH)),
        arguments("nebûtu", listOf(FRENCH, HUNGARIAN, LATVIAN)),
        arguments("forêt", listOf(FRENCH, PORTUGUESE, VIETNAMESE)),
        arguments("succèdent", listOf(FRENCH, ITALIAN, VIETNAMESE)),
        arguments("où", listOf(FRENCH, ITALIAN, VIETNAMESE)),
        arguments("tõeliseks", listOf(ESTONIAN, HUNGARIAN, PORTUGUESE, VIETNAMESE)),
        arguments("viòiem", listOf(CATALAN, ITALIAN, LATVIAN, VIETNAMESE)),
        arguments("contrôle", listOf(FRENCH, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("direktør", listOf(DANISH, NORWEGIAN)),
        arguments("vývoj", listOf(CZECH, ICELANDIC, SLOVAK, TURKISH, VIETNAMESE)),
        arguments("päralt", listOf(ESTONIAN, FINNISH, GERMAN, SLOVAK, SWEDISH)),
        arguments("labâk", listOf(LATVIAN, PORTUGUESE, ROMANIAN, TURKISH, VIETNAMESE)),
        arguments("pràctiques", listOf(CATALAN, FRENCH, ITALIAN, PORTUGUESE, VIETNAMESE)),
        arguments("überrascht", listOf(CATALAN, ESTONIAN, GERMAN, HUNGARIAN, TURKISH)),
        arguments("indebærer", listOf(DANISH, ICELANDIC, NORWEGIAN)),
        arguments("måned", listOf(DANISH, NORWEGIAN, SWEDISH)),
        arguments("zaručen", listOf(CZECH, CROATIAN, LATVIAN, LITHUANIAN, SLOVAK, SLOVENE)),
        arguments("zkouškou", listOf(CZECH, CROATIAN, LATVIAN, LITHUANIAN, SLOVAK, SLOVENE)),
        arguments("navržen", listOf(CZECH, CROATIAN, LATVIAN, LITHUANIAN, SLOVAK, SLOVENE)),
        arguments("façonnage", listOf(ALBANIAN, BASQUE, CATALAN, FRENCH, LATVIAN, PORTUGUESE, TURKISH)),
        arguments("höher", listOf(ESTONIAN, FINNISH, GERMAN, HUNGARIAN, ICELANDIC, SWEDISH, TURKISH)),
        arguments("catedráticos", listOf(CATALAN, CZECH, ICELANDIC, IRISH, HUNGARIAN, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("política", listOf(CATALAN, CZECH, ICELANDIC, IRISH, HUNGARIAN, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("música", listOf(CATALAN, CZECH, ICELANDIC, IRISH, HUNGARIAN, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("contradicció", listOf(CATALAN, HUNGARIAN, ICELANDIC, IRISH, POLISH, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("només", listOf(CATALAN, CZECH, FRENCH, HUNGARIAN, ICELANDIC, IRISH, ITALIAN, PORTUGUESE, SLOVAK, VIETNAMESE)),
        arguments("house", listOf(AFRIKAANS, ALBANIAN, BASQUE, CATALAN, CROATIAN, CZECH, DANISH, DUTCH, ENGLISH, ESTONIAN, FINNISH, FRENCH, GERMAN, HUNGARIAN, ICELANDIC, INDONESIAN, IRISH, ITALIAN, LATIN, LATVIAN, LITHUANIAN, MALAY, NORWEGIAN, POLISH, PORTUGUESE, ROMANIAN, SLOVAK, SLOVENE, SOMALI, SPANISH, SWEDISH, TAGALOG, TURKISH, VIETNAMESE, WELSH))
    )

    private fun ngramProbabilityProvider() = listOf(
        arguments(ENGLISH, Unigram("a"), 0.01),
        arguments(ENGLISH, Bigram("lt"), 0.12),
        arguments(ENGLISH, Trigram("ter"), 0.21),
        arguments(ENGLISH, Quadrigram("alte"), 0.25),
        arguments(ENGLISH, Fivegram("alter"), 0.29),

        arguments(GERMAN, Unigram("t"), 0.08),
        arguments(GERMAN, Bigram("er"), 0.18),
        arguments(GERMAN, Trigram("alt"), 0.22),
        arguments(GERMAN, Quadrigram("lter"), 0.28),
        arguments(GERMAN, Fivegram("alter"), 0.30)
    )
}
