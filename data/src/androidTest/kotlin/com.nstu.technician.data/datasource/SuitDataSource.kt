package com.nstu.technician.data.datasource

import com.nstu.technician.data.datasource.cloud.SuitCloudSource
import com.nstu.technician.data.datasource.local.SuitLocalSource
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    value = [SuitLocalSource::class, SuitCloudSource::class]
)
class SuitDataSource