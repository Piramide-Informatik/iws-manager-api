package com.iws_manager.iws_manager_api.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    // Configuración global
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("Authorization", "Content-Disposition")
            .allowCredentials(true)
            .maxAge(3600)
    }

    // Filtro CORS como respaldo
    @Bean
    fun corsFilter(): FilterRegistrationBean<CorsFilter> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            addAllowedOrigin("http://localhost:4200")
            addAllowedMethod("*")
            addAllowedHeader("*")
            addExposedHeader("Authorization")
            addExposedHeader("Content-Disposition")
            allowCredentials = true
            maxAge = 3600L
        }
        source.registerCorsConfiguration("/**", config)
        return FilterRegistrationBean<CorsFilter>(CorsFilter(source)).apply {
            order = Ordered.HIGHEST_PRECEDENCE
        }
    }
}