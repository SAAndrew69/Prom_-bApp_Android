package tech.gelab.cardiograph.singleactivity.impl;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry;
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SingleActivity_MembersInjector implements MembersInjector<SingleActivity> {
  private final Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider;

  private final Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider;

  public SingleActivity_MembersInjector(
      Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider,
      Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider) {
    this.composableEntriesMutableProvider = composableEntriesMutableProvider;
    this.aggregateEntriesMutableProvider = aggregateEntriesMutableProvider;
  }

  public static MembersInjector<SingleActivity> create(
      Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider,
      Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider) {
    return new SingleActivity_MembersInjector(composableEntriesMutableProvider, aggregateEntriesMutableProvider);
  }

  @Override
  public void injectMembers(SingleActivity instance) {
    injectComposableEntriesMutable(instance, composableEntriesMutableProvider.get());
    injectAggregateEntriesMutable(instance, aggregateEntriesMutableProvider.get());
  }

  @InjectedFieldSignature("tech.gelab.cardiograph.singleactivity.impl.SingleActivity.composableEntriesMutable")
  public static void injectComposableEntriesMutable(SingleActivity instance,
      Set<ComposableFeatureEntry> composableEntriesMutable) {
    instance.composableEntriesMutable = composableEntriesMutable;
  }

  @InjectedFieldSignature("tech.gelab.cardiograph.singleactivity.impl.SingleActivity.aggregateEntriesMutable")
  public static void injectAggregateEntriesMutable(SingleActivity instance,
      Set<AggregateFeatureEntry> aggregateEntriesMutable) {
    instance.aggregateEntriesMutable = aggregateEntriesMutable;
  }
}
