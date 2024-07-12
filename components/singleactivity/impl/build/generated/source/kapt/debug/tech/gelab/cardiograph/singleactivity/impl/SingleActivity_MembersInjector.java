package tech.gelab.cardiograph.singleactivity.impl;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry;
import tech.gelab.cardiograph.core.ui.navigation.BottomSheetFeatureEntry;
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
  private final Provider<DeeplinkHelper> deeplinkHelperProvider;

  private final Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider;

  private final Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider;

  private final Provider<Set<BottomSheetFeatureEntry>> bottomSheetEntriesMutableProvider;

  public SingleActivity_MembersInjector(Provider<DeeplinkHelper> deeplinkHelperProvider,
      Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider,
      Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider,
      Provider<Set<BottomSheetFeatureEntry>> bottomSheetEntriesMutableProvider) {
    this.deeplinkHelperProvider = deeplinkHelperProvider;
    this.composableEntriesMutableProvider = composableEntriesMutableProvider;
    this.aggregateEntriesMutableProvider = aggregateEntriesMutableProvider;
    this.bottomSheetEntriesMutableProvider = bottomSheetEntriesMutableProvider;
  }

  public static MembersInjector<SingleActivity> create(
      Provider<DeeplinkHelper> deeplinkHelperProvider,
      Provider<Set<ComposableFeatureEntry>> composableEntriesMutableProvider,
      Provider<Set<AggregateFeatureEntry>> aggregateEntriesMutableProvider,
      Provider<Set<BottomSheetFeatureEntry>> bottomSheetEntriesMutableProvider) {
    return new SingleActivity_MembersInjector(deeplinkHelperProvider, composableEntriesMutableProvider, aggregateEntriesMutableProvider, bottomSheetEntriesMutableProvider);
  }

  @Override
  public void injectMembers(SingleActivity instance) {
    injectDeeplinkHelper(instance, deeplinkHelperProvider.get());
    injectComposableEntriesMutable(instance, composableEntriesMutableProvider.get());
    injectAggregateEntriesMutable(instance, aggregateEntriesMutableProvider.get());
    injectBottomSheetEntriesMutable(instance, bottomSheetEntriesMutableProvider.get());
  }

  @InjectedFieldSignature("tech.gelab.cardiograph.singleactivity.impl.SingleActivity.deeplinkHelper")
  public static void injectDeeplinkHelper(SingleActivity instance, DeeplinkHelper deeplinkHelper) {
    instance.deeplinkHelper = deeplinkHelper;
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

  @InjectedFieldSignature("tech.gelab.cardiograph.singleactivity.impl.SingleActivity.bottomSheetEntriesMutable")
  public static void injectBottomSheetEntriesMutable(SingleActivity instance,
      Set<BottomSheetFeatureEntry> bottomSheetEntriesMutable) {
    instance.bottomSheetEntriesMutable = bottomSheetEntriesMutable;
  }
}
