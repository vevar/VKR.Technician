package com.nstu.technician.data.datasource.cloud

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [ComponentCloudSourceTest::class, ComponentTypeCloudSourceTest::class, ImplementsCloudSourceTest::class, ShiftCloudSourceTest::class,
        TechnicianCloudSourceTest::class, UserCloudSourceTest::class]
)
class SuitCloudSource