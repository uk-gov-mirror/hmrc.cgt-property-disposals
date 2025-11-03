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

package uk.gov.hmrc.cgtpropertydisposals.repositories.upscan

import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import uk.gov.hmrc.cgtpropertydisposals.models.generators.Generators.sample
import uk.gov.hmrc.cgtpropertydisposals.models.generators.UpscanGen.{uploadReferenceGen, upscanUploadGen}
import uk.gov.hmrc.cgtpropertydisposals.models.upscan.{UploadReference, UpscanUpload}
import uk.gov.hmrc.cgtpropertydisposals.repositories.DefaultCurrentInstant
import uk.gov.hmrc.mongo.test.MongoSupport

import scala.concurrent.ExecutionContext.Implicits.global

class UpscanRepositoryFailureSpec extends AnyWordSpec with Matchers with MongoSupport with BeforeAndAfterAll {
  val config: Configuration = Configuration(
    ConfigFactory.parseString(
      """
        | mongodb.upscan.expiry-time = 7days
        |""".stripMargin
    )
  )

  val defaultCurrentInstant = new DefaultCurrentInstant

  val repository = new DefaultUpscanRepository(mongoComponent, config, defaultCurrentInstant)

  "Upscan Repository" when {
    "inserting" should {
      "return an error if there is a failure" in {
        val upscanUpload = sample[UpscanUpload]
        repository.insert(upscanUpload).value.map { either =>
          either.isLeft shouldBe true
        }
      }
    }

    "updating an upscan upload document" should {
      "return an error if there is a failure" in {
        repository
          .select(sample[UploadReference])
          .value
          .map { either =>
            either.isLeft shouldBe true
          }
      }
    }

    "selecting an upscan upload document" should {
      "return an error if there is a failure" in {
        repository
          .select(sample[UploadReference])
          .value
          .map { either =>
            either.isLeft shouldBe true
          }
      }
    }

    "selecting all upscan upload documents" should {
      "return an error if there is a failure" in {
        repository
          .selectAll(List(sample[UploadReference]))
          .value
          .map { either =>
            either.isLeft shouldBe true
          }
      }
    }
  }

}
