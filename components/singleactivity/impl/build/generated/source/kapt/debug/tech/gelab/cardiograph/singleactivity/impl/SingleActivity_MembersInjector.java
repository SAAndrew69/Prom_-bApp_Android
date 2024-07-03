package tech.gelab.cardiograph.singleactivity.impl;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry;

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
  private final Provider<Set<ComposableFeatureEntry>> composableEntriessMutableProvider;

  public SingleActivity_MembersInjector(
      Provider<Set<ComposableFeatureEntry>> composableEntriessMutableProvider) {
    this.composableEntriessMutableProvider = composableEntriessMutableProvider;
  }

  public static MembersInjector<SingleActivity> create(
      Provider<Set<ComposableFeatureEntry>> composableEntriessMutableProvider) {
    return new SingleActivity_MembersInjector(composableEntriessMutableProvider);
  }

  @Override
  public void injectMembers(SingleActivity instance) {
    injectComposableEntriessMutable(instance, composableEntriessMutableProvider.get());
  }

  @InjectedFieldSignature("tech.gelab.cardiograph.singleactivity.impl.SingleActivity.composableEntriessMutable")
  public static void injectComposableEntriessMutable(SingleActivity instance,
      Set<ComposableFeatureEntry> composableEntriessMutable) {
    instance.composableEntriessMutable = composableEntriessMutable;
  }
}
