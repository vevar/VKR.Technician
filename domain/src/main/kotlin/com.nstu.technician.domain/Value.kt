package com.nstu.technician.domain


const val NONE = 0L
//--------------------------------------------------------------------------------------------------
const val GeoNone = 0                  // Координаты недоступны
const val GeoNet = 1                   // Координаты от сети (вышек)
const val GeoGPS = 2                   // Координаты от GPS
//--------------------------------------------------------------------------------------------------
const val HTTPAuthorization = 401
const val HTTPNotFound = 404
const val HTTPRequestError = 400
const val HTTPException = 500
const val HTTPServiceUnavailable = 503  // Сервис недоступен
//----------------------------------------------------------------------
const val UndefinedType = 0            // Неопределенный тип
//----------------- Типы артефактов-------------------------------------
const val ArtifactImageType = 1
const val ArtifactVideoType = 2
const val ArtifactAudioType = 3
const val ArtifactDocType = 4
const val ArtifactTextType = 5
const val ArtifactOtherType = 6
val ArtifactTypeNames = arrayOf("-----", "Фото", "Видео", "Аудио", "Текст", "Документ", "Прочее")
val ArtifactDirNames = arrayOf("-----", "Image", "Video", "Audio", "Text", "Document", "Other")
//------------------ Типы проблем------------------------------------------
const val ProblemQRCode = 1
const val ProblemCantDo = 2
const val ProblemEquipment = 3
const val ProblemOther = 4
val ProblemTypeNames =
    arrayOf("-----", "Проблема с QR", "Невозможность выполнения", "Необходимость оборудования", "Другое")
//------------------- Статус объекта-----------------------------------------
const val FacilityStateOK = 1
const val FacilityStateNeedExtention = 2
const val FacilityStateMissing = 3
val FacilityStates = arrayOf("-----", "Активен", "Требуется продление", "Нет контракта")
//------------------ Вид обслуживания (заявки) ---------------------------------------
const val MMonthly = 1     // Ежемесячное ТО
const val MSingle = 2      // Разовая (Заявка техника)
const val MUnplanned = 3   // Внеплановая (Голосовое сообщение)
const val MRepeated = 4    // Повторная
val MaintenanceTypes =
    arrayOf("-----", "Ежемесячное ТО", "Разовая (от техника)", "Внеплановая (Голосовое сообщение)", "Повторная")
//--------------- Бизнес-состояния техника ----------------------------------------
const val TStateLogOff = 0     // LogOff
const val TStateOff = 1        // Не на смене
const val TStateOnWay = 2      // В пути к объекту
const val TStateOnFacility = 3 // На объекте
const val TStateMaintenance = 4// Обслуживание
const val TStateNotOnPlace = 5 // Отлучился с объекта
const val TStateNoAccess = 6   // Недоступен (нет связи или координат)
const val TStateProblem = 7    // Проблема обслуживания
const val TStateForce = 8      // Форс-мажор
val TStateList = arrayOf(
    "Разлогинен",
    "Не на смене",
    "В пути к объекту",
    "На объекте",
    "Обслуживание",
    "Отлучился",
    "Недоступен",
    "Проблема обслуживания",
    "Форс-мажор"
)
//------------------ Состояние заявки   ---- Maintenance
const val MSEdit = 1               // редактируется
const val MSReady = 2              // сфоррмирована, ожидает распределения
const val MSWaitForComponents = 3  // Ожидание поступления КИ
const val MSInPlan = 4             // В плане
const val MSOverTime = 5           // Превышено время
const val MSInProcess = 6          // Обслуживается
const val MSHasProblem = 7         // Проблема
const val MSDone = 8               // Выполнена
val MaintenanceStates = arrayOf(
    "-----",
    "Редактируется",
    "Не назначена",
    "Ожидание КИ",
    "В плане",
    "Превышено время",
    "Обслуживается",
    "Проблема",
    "Выполнена"
)
//------------------ Состояние заявки на комплектующие  ----ComponentRequest
const val СRCreated = 1      // Выставлена техником
const val СRInProcess = 2    // Обслуживается кладовщиком
const val СRWait = 3         // Ожидание поступления КИ
const val СRRejected = 4     // Отклолнена
const val СRDone = 5         // Выполнена
val CRequestStates = arrayOf("-----", "Выставлена", "Обслуживается", "Ожидание КИ", "Отклонена", "Выполнена")
//------------------ Состояние договора ------- Contract
const val ContractOK = 1
const val ContractNeedExtention = 2
const val ContractContinued = 3
const val ContractDone = 4
val ContracStates = arrayOf("-----", "Активен", "Требуется продление", "Пролонгирован", "Завершен")
//------------------ Состояние Счета ------PaymentInvoice
const val PICreated = 1           // Создан, дата не наступила
const val PINeedToSend = 2        // Дата наступила, требуется выставить
const val PIWasSended = 3         // Выставлен
const val PIDone = 4              // Возвращен
const val PIError = 5             // Форсмажор
val PIStates = arrayOf("-----", "Дата не наступила", "Дата наступила", "Выставлен", "Оплачен", "Форсмажор")
//------------------ Состояние Акта -------WorkCompletionReport
const val WCRCreated = 1           // Создан, дата не наступила
const val WCRNeedToPrint = 2       // Дата наступила
const val WCRWasSended = 3         // Отослан
const val WCRDone = 4              // Возвращен
const val WCRRejected = 4          // Отклонен
val WCRStates = arrayOf("-----", "Дата не наступила", "Дата наступила", "Отослан", "Возвращен", "Отклонен")
//------------------- Состояние работы -------------------------------------------------------------------------
const val JobInPlan = 1
const val JobInProcess = 2
const val JobDone = 3
const val JobFail = 4
val JobState = arrayOf("-----", "В плане", "В процессе", "Выполнено", "Проблема")
//------------------- Вид уведомления  -------------------------------------------------------------------------
const val NTOverTime = 1
const val NTShiftChanged = 2
const val NTInaccessible = 3
const val NTAccessible = 4
const val NTLeaveFacility = 5
const val NTBackToFacility = 6
const val NTProblem = 6
val NTypes = arrayOf(
    "-----",
    "Превышего время",
    "Изменен план смены",
    "Не в сети",
    "В сети",
    "Покинул объект",
    "Вернулся на объект",
    "Проблема у техника"
)

//------------------- Состояние уведомлнния  -------------------------------------------------------------------------
const val NSSend = 1
const val NSReceived = 2
const val NSInProcess = 3
const val NSClosed = 4
val NState = arrayOf("-----", "Передано", "Просмотрено", "В работе", "Закрыто")
//------------------- Настройка параметров МК --------------------------------------------------------------------
const val WSgpsOnWayPeriod = 2           // Период GPS на пути к объекту (мин)
const val WSgpsOnFacilityPeriod = 10     // Период GPS на  объекте (мин)
const val WSminDistanceToFacility = 200  // Мин. расстояние до объекта для активации
const val WSworkDayBegin = 10            // Начало рабочего дня
const val WSworkDayEnd = 18              // Конец рабочего дня
const val WSgpsValidDelay = 5            // Интервал УСТАРЕВАНИЯ последних координат (мин)
//------------------------ Типы улиц и нас пунктов -------------------------------------------------
const val CTown = 1
const val CCountry = 2
val TypesCity = arrayOf("", "г.", "пос.")
const val SStreet = 0x10
const val SProspect = 0x20
val SWay = 0x30
val TypesStreet = arrayOf("", "ул.", "пр.", "ш.")
const val HHome = 0x100
const val HCorpus = 0x200
val TypesHome = arrayOf("", "д.", "корп.")
const val OOffice = 0x1000
const val OCabinet = 0x2000
const val OQuart = 0x3000
val TypesOffice = arrayOf("", "оф.", "к.", "кв.")

