/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.http;

import io.netty.buffer.ByteBuf;
import ratpack.api.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Data that potentially has a content type.
 */
public interface TypedData {

  /**
   * The type of the data.
   *
   * @return The type of the data, or {@code null} if no type was advertised.
   */
  @Nullable
  MediaType getContentType();

  /**
   * The data as text.
   * <p>
   * If a content type was provided, and it provided a charset parameter, that charset will be used to decode the text.
   * If no charset was provided, {@code UTF-8} will be assumed.
   * <p>
   * This can lead to incorrect results for non {@code text/*} type content types.
   * For example, {@code application/json} is implicitly {@code UTF-8} but this method will not know that.
   *
   * @return The data decoded as text
   */
  String getText();

  /**
   * The raw data as bytes.
   *
   * @return the raw data as bytes.
   */
  byte[] getBytes();

  /**
   * The raw data as a (unmodifiable) buffer.
   *
   * @return the raw data as bytes.
   */
  ByteBuf getBuffer();

  /**
   * Writes the data to the given output stream.
   * <p>
   * This method does not flush or close the stream.
   *
   * @param outputStream The stream to write to
   * @throws IOException any thrown when writing to the output stream
   */
  void writeTo(OutputStream outputStream) throws IOException;

  /**
   * An input stream of the data.
   *
   * @return an input stream of the data.
   */
  InputStream getInputStream();

}
