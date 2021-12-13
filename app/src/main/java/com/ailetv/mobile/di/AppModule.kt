package com.ailetv.mobile.di

import com.ailetv.mobile.data.model.resource.ContractPOJO
import com.ailetv.mobile.data.model.resource.util.AuthUtilModel
import com.ailetv.mobile.data.networking.api.AileTvApiClient
import com.ailetv.mobile.data.repo.*
import com.ailetv.mobile.ui.auth.login.LoginVM
import com.ailetv.mobile.ui.auth.otp.OtpVM
import com.ailetv.mobile.ui.dashboard.campaigns.CampaignsVM
import com.ailetv.mobile.ui.dashboard.main.MainVM
import com.ailetv.mobile.ui.dashboard.main.bonus.BonusVM
import com.ailetv.mobile.ui.dashboard.main.internet.InternetVM
import com.ailetv.mobile.ui.dashboard.main.ipTv.IpTvVM
import com.ailetv.mobile.ui.dashboard.main.payment.PaymentAmountVM
import com.ailetv.mobile.ui.dashboard.main.tv.TvVM
import com.ailetv.mobile.ui.dashboard.myAccount.MyAccountVM
import com.ailetv.mobile.ui.dashboard.notifications.NotificationsVM
import com.ailetv.mobile.ui.dashboard.services.ServicesVM
import com.ailetv.mobile.ui.splash.SplashVM
import com.ailetv.mobile.ui.web.WebVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AileTvApiClient.provideAuthApi() }
    single { AileTvApiClient.provideMainApi() }
    single { AileTvApiClient.provideCampaignsApi() }
    single { AileTvApiClient.provideNotificationsApi() }
    single { AileTvApiClient.provideServicesApi() }
    single { AileTvApiClient.provideMyAccountApi() }
    single { AileTvApiClient.providePaymentApi() }
    single { AileTvApiClient.provideOthersApi() }

    viewModel { SplashVM(get()) }
    viewModel { WebVM() }

    factory { AuthRepo(get()) }
    viewModel { LoginVM(get()) }
    viewModel { (utilModel: AuthUtilModel) -> OtpVM(get(), utilModel) }

    factory { MainRepo(get()) }
    viewModel { MainVM(get()) }
    viewModel { (contractPojo: ContractPOJO) -> InternetVM(get(), contractPojo) }
    viewModel { (contractPojo: ContractPOJO) -> TvVM(get(), contractPojo) }
    viewModel { (contractPojo: ContractPOJO) -> IpTvVM(get(), contractPojo) }

    factory { CampaignsRepo(get()) }
    viewModel { CampaignsVM(get()) }

    factory { NotificationsRepo(get()) }
    viewModel { NotificationsVM(get()) }

    factory { ServicesRepo(get()) }
    viewModel { ServicesVM(get()) }

    factory { MyAccountRepo(get()) }
    viewModel { MyAccountVM(get()) }

    factory { BonusRepo(get(),get()) }
    viewModel { BonusVM(get()) }

    factory { PaymentRepo(get()) }
    viewModel { (contractPojo: ContractPOJO) -> PaymentAmountVM(get(), contractPojo) }
}