/*
 * Copyright 2014 the original author or authors.
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

package ratpack.exec;

import ratpack.func.Action;

/**
 * Convenience base for {@code Action<Fulfiller<T>>} implementations.
 *
 * @param <T> the type of promised value.
 * @see ExecControl#promise(Action)
 * @see Fulfiller
 */
public abstract class Fulfillment<T> implements Fulfiller<T>, Action<Fulfiller<T>> {

  private Fulfiller<T> fulfiller;

  /**
   * Fulfill with an error result.
   *
   * @param throwable the error result
   */
  @Override
  public void error(Throwable throwable) {
    fulfiller.error(throwable);
  }

  /**
   * Fulfill with a success result.
   *
   * @param value the value
   */
  @Override
  public void success(T value) {
    fulfiller.success(value);
  }

  /**
   * Delegates to {@link #execute()}, using the given {@code fulfiller} for delegation.
   *
   * @param fulfiller the promise fulfiller
   * @throws Exception Any thrown by {@link #execute()}
   */
  @Override
  public final void execute(Fulfiller<T> fulfiller) throws Exception {
    this.fulfiller = fulfiller;
    try {
      execute();
    } finally {
      this.fulfiller = null;
    }
  }

  /**
   * Implementations can naturally use the {@link Fulfiller} methods for the duration of this method.
   *
   * @throws Exception any exception thrown while fulfilling
   */
  protected abstract void execute() throws Exception;

}