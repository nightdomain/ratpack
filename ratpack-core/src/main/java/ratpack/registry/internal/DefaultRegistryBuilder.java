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

package ratpack.registry.internal;

import com.google.common.collect.ImmutableList;
import ratpack.registry.Registry;
import ratpack.registry.RegistryBuilder;
import ratpack.util.Factory;

import static ratpack.registry.Registries.join;

public class DefaultRegistryBuilder implements RegistryBuilder {

  private final ImmutableList.Builder<RegistryEntry<?>> builder = ImmutableList.builder();

  @Override
  public <O> RegistryBuilder add(Class<? super O> type, O object) {
    //noinspection unchecked
    builder.add(new DefaultRegistryEntry<>(type, object));
    return this;
  }

  @Override
  public <O> RegistryBuilder add(O object) {
    @SuppressWarnings("unchecked") Class<O> cast = (Class<O>) object.getClass();
    return add(cast, object);
  }

  @Override
  public <O> RegistryBuilder add(Class<O> type, Factory<? extends O> object) {
    //noinspection unchecked
    builder.add(new LazyRegistryEntry<>(type, object));
    return this;
  }

  @Override
  public Registry build() {
    return new CachingRegistry(new DefaultRegistry(builder.build()));
  }

  @Override
  public Registry build(Registry parent) {
    return join(parent, new DefaultRegistry(builder.build()));
  }

}