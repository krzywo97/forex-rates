package pl.makrohard.forexrates.application

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.makrohard.forexrates.data.repository.FixerRepository
import pl.makrohard.forexrates.domain.repository.RatesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRatesRepository(fixerRepository: FixerRepository): RatesRepository
}