package guru.sfg.brewery.security.permissionsCustAnnotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sousaJ on 17/06/2021
 * in package - guru.sfg.brewery.security.permissionsCustAnnotations
 **/
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.create')")
public @interface CustomerCreatePermission {}
