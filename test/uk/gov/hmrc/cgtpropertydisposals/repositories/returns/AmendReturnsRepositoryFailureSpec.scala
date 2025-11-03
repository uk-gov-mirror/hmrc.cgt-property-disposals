/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.cgtpropertydisposals.repositories.returns

import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import uk.gov.hmrc.cgtpropertydisposals.models.generators.Generators.sample
import uk.gov.hmrc.cgtpropertydisposals.models.generators.IdGen.given
import uk.gov.hmrc.cgtpropertydisposals.models.generators.SubmitReturnGen.given
import uk.gov.hmrc.cgtpropertydisposals.models.ids.CgtReference
import uk.gov.hmrc.cgtpropertydisposals.models.returns.SubmitReturnRequest
import uk.gov.hmrc.cgtpropertydisposals.repositories.DefaultCurrentInstant
import uk.gov.hmrc.mongo.test.MongoSupport

import scala.concurrent.ExecutionContext.Implicits.global

class AmendReturnsRepositoryFailureSpec extends AnyWordSpec with Matchers with MongoSupport with BeforeAndAfterAll {
  private val config = Configuration(
    ConfigFactory.parseString(
      """
        | mongodb.amend-returns.expiry-time = 24hours
        |""".stripMargin
    )
  )

  val defaultCurrentInstant = new DefaultCurrentInstant

  val repository = new DefaultAmendReturnsRepository(mongoComponent, config, defaultCurrentInstant)

  private val submitReturnRequest = sample[SubmitReturnRequest]
  private val cgtReference        = sample[CgtReference]

  "AmendReturnsRepository" when {

    "inserting" should {
      "insert an amend return request successfully" in {
        repository.save(submitReturnRequest).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }

    "getting" should {
      "retrieve an existing record" in {
        repository.fetch(cgtReference).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }
  }
}
