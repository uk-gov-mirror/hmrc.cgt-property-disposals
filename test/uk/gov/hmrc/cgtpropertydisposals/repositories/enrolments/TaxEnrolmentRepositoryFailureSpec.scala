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

package uk.gov.hmrc.cgtpropertydisposals.repositories.enrolments

import org.scalacheck.Arbitrary
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.test.Helpers.*
import uk.gov.hmrc.cgtpropertydisposals.models.enrolments.TaxEnrolmentRequest
import uk.gov.hmrc.cgtpropertydisposals.models.generators.Generators.*
import uk.gov.hmrc.cgtpropertydisposals.models.generators.TaxEnrolmentGen.given
import uk.gov.hmrc.mongo.test.MongoSupport

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global

class TaxEnrolmentRepositoryFailureSpec extends AnyWordSpec with Matchers with MongoSupport with BeforeAndAfterAll {

  val repository = new DefaultTaxEnrolmentRepository(mongoComponent)

  implicit val arbLocalDateTime: Arbitrary[LocalDateTime] =
    Arbitrary(LocalDateTime.now())

  val taxEnrolmentRequest: TaxEnrolmentRequest = sample[TaxEnrolmentRequest]

  "The Tax Enrolment Retry repository" when {

    "inserting into a broken repository" should {
      "fail the insert" in {
        repository.save(taxEnrolmentRequest).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }

    "getting from a broken repository" should {
      "fail the get" in {
        repository.get(taxEnrolmentRequest.ggCredId).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }

    "deleting from a broken repository" should {
      "fail the delete" in {
        repository.delete(taxEnrolmentRequest.ggCredId).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }
    "updating on a broken repository"   should {
      val updatedTaxEnrolmentRequest = sample[TaxEnrolmentRequest]
      "fail to update" in {
        await(repository.save(taxEnrolmentRequest).value)
        repository.update(taxEnrolmentRequest.ggCredId, updatedTaxEnrolmentRequest).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }
  }
}
