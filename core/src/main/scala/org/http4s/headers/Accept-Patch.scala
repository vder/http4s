/*
 * Copyright 2013 http4s.org
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

package org.http4s

package headers

import org.http4s.util.Renderer
import cats.data.NonEmptyList
import org.http4s.internal.parsing.Rfc7230
import org.typelevel.ci.CIString

object `Accept-Patch` extends HeaderKey.Internal[`Accept-Patch`] with HeaderKey.Recurring {

  override def parse(s: String): ParseResult[`Accept-Patch`] =
    ParseResult.fromParser(parser, "Invalid Accept-Patch header")(s)

  private[http4s] val parser =
    Rfc7230.headerRep1(MediaType.parser).map(`Accept-Patch`(_))

  implicit val headerInstance: v2.Header[`Accept-Patch`, v2.Header.Recurring] =
    v2.Header.createRendered(
      CIString("Accept-Patch"),
      _.values,
      ParseResult.fromParser(parser, "Invalid Accept-Patch header")
    )

  implicit val headerSemigroupInstance: cats.Semigroup[`Accept-Patch`] =
    (a, b) => `Accept-Patch`(a.values.concatNel(b.values))
}

// see https://tools.ietf.org/html/rfc5789#section-3.1
final case class `Accept-Patch` private (values: NonEmptyList[MediaType])
    extends Header.RecurringRenderer {

  type Value = MediaType
  val renderer = Renderer[MediaType]

  override def key: `Accept-Patch`.type = `Accept-Patch`

}
