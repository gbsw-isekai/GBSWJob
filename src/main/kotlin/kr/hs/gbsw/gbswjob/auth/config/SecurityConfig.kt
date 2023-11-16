package kr.hs.gbsw.gbswjob.auth.config

import kr.hs.gbsw.gbswjob.auth.JwtUtils
import kr.hs.gbsw.gbswjob.auth.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfig(
//    val accessDeniedHandler: CustomAccessDeniedHandler,
//    val unauthorizedHandler: JwtAuthenticationEntryPoint,
    val authProviders: List<AuthenticationProvider>?
) : EnvironmentAware {
    lateinit var env: Environment

    override fun setEnvironment(environment: Environment) {
        this.env = environment
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Autowired
    fun registerProvider(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authProviders?.forEach(authenticationManagerBuilder::authenticationProvider)
    }

    @Bean
    fun filterChain(authenticationConfiguration: AuthenticationConfiguration, http: HttpSecurity, jwtUtils: JwtUtils): SecurityFilterChain {
        http.headers {
            it.frameOptions {
                it.disable()
            }
        }
        http.cors {
            it.configurationSource(corsConfigurationSource())
        }
        http.csrf { it.disable().addFilterBefore(authenticationFilter(authenticationManager(authenticationConfiguration), jwtUtils), UsernamePasswordAuthenticationFilter::class.java) }
        http.exceptionHandling {
//            it.authenticationEntryPoint(unauthorizedHandler)
//            it.accessDeniedHandler(accessDeniedHandler)
        }
        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        http.authorizeHttpRequests {
//            it.requestMatchers(
//                "/",
//                "/favicon.ico",
//                "/**/*.png",
//                "/**/*.gif",
//                "/**/*.svg",
//                "/**/*.jpg",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js"
//            ).permitAll()
//
//            it.requestMatchers(
//                    "/auth/*",
//                    "/users",
//                    "/application/healthcheck",
//                    "/to/*",
//            ).permitAll()

//            it.requestMatchers(HttpMethod.OPTIONS, "/**/*")
//                    .permitAll()

            it.anyRequest()
                    .permitAll()
        }

        authProviders?.forEach {
            http.authenticationProvider(it)
        }

        return http.build()
    }

    /**
     * CORS 설정을 위한 Bean입니다.
     *
     * @return corsConfigurationSource
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = Arrays.asList(
            // 추후 도메인 추가
                "*"
        )
        if (Arrays.stream(env.activeProfiles)
                        .anyMatch { profile: String -> "dev" == profile || "local" == profile || "linkprod" == profile || "staging" == profile }
        ) {
            configuration.allowedOriginPatterns = Arrays.asList(
                    "*"
            )
        }
        configuration.allowedMethods = Arrays.asList(
                "HEAD",
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        )
        configuration.allowedHeaders = Arrays.asList(
                "Authorization",
                "TOKEN_ID",
                "X-Requested-With",
                "Content-Type",
                "Content-Length",
                "Cache-Control",
                "KakaoAK",
                "Cookie",
                "Tracker"
        )
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * Jwt토큰 필터 설정을 위한 Bean입니다.
     *
     * @return jwtAuthenticationFilter
     */
    @Bean
    fun authenticationFilter(authenticationManager: AuthenticationManager, jwtUtils: JwtUtils): JwtAuthenticationFilter? {
        return JwtAuthenticationFilter(authenticationManager, jwtUtils)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
