package kz.witme.project.dashboard.di

import kz.witme.project.dashboard.DashboardViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val featureDashboardModule: Module = module {
    factory { DashboardViewModel() }
}