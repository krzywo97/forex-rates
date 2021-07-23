package pl.makrohard.forexrates.application

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pl.makrohard.forexrates.domain.usecase.GetRatesOverviewUseCase
import pl.makrohard.forexrates.domain.usecase.GetRatesOverviewUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetRatesOverviewUseCase(getRatesOverviewUseCaseImpl: GetRatesOverviewUseCaseImpl): GetRatesOverviewUseCase
}